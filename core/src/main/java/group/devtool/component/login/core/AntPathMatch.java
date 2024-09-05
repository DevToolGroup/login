package group.devtool.component.login.core;

import jdk.internal.jline.internal.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntPathMatch {

    public static AntPathMatch INS = new AntPathMatch();

    private static final String DEFAULT_PATH_SEPARATOR = "/";

    private static final int CACHE_TURNOFF_THRESHOLD = 65536;

    private static final char[] WILDCARD_CHARS = {'*', '?', '{'};

    private static final String[] EMPTY_STRING_ARRAY = {};

    private final String pathSeparator;

    @Nullable
    private volatile Boolean cachePatterns;

    private final Map<String, String[]> tokenizedPatternCache = new ConcurrentHashMap<>(256);

    final Map<String, AntPathStringMatcher> stringMatcherCache = new ConcurrentHashMap<>(256);


    private AntPathMatch() {
        this.pathSeparator = DEFAULT_PATH_SEPARATOR;
    }

    private void deactivatePatternCache() {
        this.cachePatterns = false;
        this.tokenizedPatternCache.clear();
        this.stringMatcherCache.clear();
    }

    public boolean match(String pattern, String path) {
        if (path == null || path.startsWith(this.pathSeparator) != pattern.startsWith(this.pathSeparator)) {
            return false;
        }

        String[] dirs = tokenizePattern(pattern);
        if (!isPotentialMatch(path, dirs)) {
            return false;
        }

        String[] pathDirs = tokenizePath(path);
        int patternIdxStart = 0;
        int patternIdxEnd = dirs.length - 1;
        int pathIdxStart = 0;
        int pathIdxEnd = pathDirs.length - 1;

        // Match all elements up to the first **
        while (patternIdxStart <= patternIdxEnd && pathIdxStart <= pathIdxEnd) {
            String dir = dirs[patternIdxStart];
            if ("**".equals(dir)) {
                break;
            }
            if (!matchStrings(dir, pathDirs[pathIdxStart])) {
                return false;
            }
            patternIdxStart++;
            pathIdxStart++;
        }

        if (pathIdxStart > pathIdxEnd) {
            // Path is exhausted, only match if rest of pattern is * or **'s
            if (patternIdxStart > patternIdxEnd) {
                return (pattern.endsWith(this.pathSeparator) == path.endsWith(this.pathSeparator));
            }
            if (patternIdxStart == patternIdxEnd && dirs[patternIdxStart].equals("*") && path.endsWith(this.pathSeparator)) {
                return true;
            }
            for (int i = patternIdxStart; i <= patternIdxEnd; i++) {
                if (!dirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        } else if (patternIdxStart > patternIdxEnd) {
            // String not exhausted, but pattern is. Failure.
            return false;
        }

        // up to last '**'
        while (patternIdxStart <= patternIdxEnd && pathIdxStart <= pathIdxEnd) {
            String dir = dirs[patternIdxEnd];
            if (dir.equals("**")) {
                break;
            }
            if (!matchStrings(dir, pathDirs[pathIdxEnd])) {
                return false;
            }
            if (patternIdxEnd == (dirs.length - 1)
                    && pattern.endsWith(this.pathSeparator) != path.endsWith(this.pathSeparator)) {
                return false;
            }
            patternIdxEnd--;
            pathIdxEnd--;
        }
        if (pathIdxStart > pathIdxEnd) {
            for (int i = patternIdxStart; i <= patternIdxEnd; i++) {
                if (!dirs[i].equals("**")) {
                    return false;
                }
            }
            return true;
        }

        while (patternIdxStart != patternIdxEnd && pathIdxStart <= pathIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patternIdxStart + 1; i <= patternIdxEnd; i++) {
                if (dirs[i].equals("**")) {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == patternIdxStart + 1) {
                // '**/**' situation, so skip one
                patternIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - patternIdxStart - 1);
            int strLength = (pathIdxEnd - pathIdxStart + 1);
            int foundIdx = -1;

            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    String subPat = dirs[patternIdxStart + j + 1];
                    String subStr = pathDirs[pathIdxStart + i + j];
                    if (!matchStrings(subPat, subStr)) {
                        continue strLoop;
                    }
                }
                foundIdx = pathIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            patternIdxStart = patIdxTmp;
            pathIdxStart = foundIdx + patLength;
        }

        for (int i = patternIdxStart; i <= patternIdxEnd; i++) {
            if (!dirs[i].equals("**")) {
                return false;
            }
        }

        return true;
    }

    private boolean isPotentialMatch(String path, String[] dirs) {
        int pos = 0;
        for (String dir : dirs) {
            int skipped = skipSeparator(path, pos, this.pathSeparator);
            pos += skipped;
            skipped = skipSegment(path, pos, dir);
            if (skipped < dir.length()) {
                return (skipped > 0 || (dir.length() > 0 && isWildcardChar(dir.charAt(0))));
            }
            pos += skipped;
        }
        return true;
    }

    private int skipSegment(String path, int pos, String prefix) {
        int skipped = 0;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (isWildcardChar(c)) {
                return skipped;
            }
            int currPos = pos + skipped;
            if (currPos >= path.length()) {
                return 0;
            }
            if (c == path.charAt(currPos)) {
                skipped++;
            }
        }
        return skipped;
    }

    private int skipSeparator(String path, int pos, String separator) {
        int skipped = 0;
        while (path.startsWith(separator, pos + skipped)) {
            skipped += separator.length();
        }
        return skipped;
    }

    private boolean isWildcardChar(char c) {
        for (char candidate : WILDCARD_CHARS) {
            if (c == candidate) {
                return true;
            }
        }
        return false;
    }

    private String[] tokenizePattern(String pattern) {
        String[] tokenized = null;
        if (cachePatterns == null || cachePatterns) {
            tokenized = this.tokenizedPatternCache.get(pattern);
        }
        if (tokenized == null) {
            tokenized = tokenizePath(pattern);
            if (cachePatterns == null && this.tokenizedPatternCache.size() >= CACHE_TURNOFF_THRESHOLD) {
                deactivatePatternCache();
                return tokenized;
            }
            if (cachePatterns == null || cachePatterns) {
                this.tokenizedPatternCache.put(pattern, tokenized);
            }
        }
        return tokenized;
    }

    private String[] tokenizePath(String path) {
        return tokenizeToStringArray(path, this.pathSeparator);
    }

    private static String[] tokenizeToStringArray(String str, String delimiters) {
        if (str == null) {
            return EMPTY_STRING_ARRAY;
        }

        StringTokenizer st = new StringTokenizer(str, delimiters);
        List<String> tokens = new ArrayList<>();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.length() > 0) {
                tokens.add(token);
            }
        }
        return toStringArray(tokens);
    }

    public static String[] toStringArray(@Nullable Collection<String> collection) {
        return (Objects.isNull(collection) || collection.isEmpty() ? EMPTY_STRING_ARRAY : collection.toArray(EMPTY_STRING_ARRAY));
    }

    private boolean matchStrings(String pattern, String str) {
        return getStringMatcher(pattern).matchStrings(str, null);
    }

    private AntPathStringMatcher getStringMatcher(String pattern) {
        AntPathStringMatcher matcher = null;
        if (cachePatterns == null || cachePatterns) {
            matcher = this.stringMatcherCache.get(pattern);
        }
        if (matcher == null) {
            matcher = new AntPathStringMatcher(pattern);
            if (cachePatterns == null && this.stringMatcherCache.size() >= CACHE_TURNOFF_THRESHOLD) {
                deactivatePatternCache();
                return matcher;
            }
            if (cachePatterns == null || cachePatterns) {
                this.stringMatcherCache.put(pattern, matcher);
            }
        }
        return matcher;
    }

    private static class AntPathStringMatcher {

        private static final Pattern GLOB_PATTERN = Pattern.compile("\\?|\\*|\\{((?:\\{[^/]+?\\}|[^/{}]|\\\\[{}])+?)\\}");

        private static final String DEFAULT_VARIABLE_PATTERN = "((?s).*)";

        private final String rawPattern;

        private final boolean caseSensitive;

        private final boolean exactMatch;

        @Nullable
        private final Pattern pattern;

        private final List<String> variableNames = new ArrayList<>();

        public AntPathStringMatcher(String pattern) {
            this(pattern, true);
        }

        public AntPathStringMatcher(String pattern, boolean caseSensitive) {
            this.rawPattern = pattern;
            this.caseSensitive = caseSensitive;
            StringBuilder patternBuilder = new StringBuilder();
            Matcher matcher = GLOB_PATTERN.matcher(pattern);
            int end = 0;
            while (matcher.find()) {
                patternBuilder.append(quote(pattern, end, matcher.start()));
                String match = matcher.group();
                if ("?".equals(match)) {
                    patternBuilder.append('.');
                } else if ("*".equals(match)) {
                    patternBuilder.append(".*");
                } else if (match.startsWith("{") && match.endsWith("}")) {
                    int colonIdx = match.indexOf(':');
                    if (colonIdx == -1) {
                        patternBuilder.append(DEFAULT_VARIABLE_PATTERN);
                        this.variableNames.add(matcher.group(1));
                    } else {
                        String variablePattern = match.substring(colonIdx + 1, match.length() - 1);
                        patternBuilder.append('(');
                        patternBuilder.append(variablePattern);
                        patternBuilder.append(')');
                        String variableName = match.substring(1, colonIdx);
                        this.variableNames.add(variableName);
                    }
                }
                end = matcher.end();
            }
            // No glob pattern was found, this is an exact String match
            if (end == 0) {
                this.exactMatch = true;
                this.pattern = null;
            } else {
                this.exactMatch = false;
                patternBuilder.append(quote(pattern, end, pattern.length()));
                this.pattern = Pattern.compile(patternBuilder.toString(),
                        Pattern.DOTALL | (this.caseSensitive ? 0 : Pattern.CASE_INSENSITIVE));
            }
        }

        private String quote(String s, int start, int end) {
            if (start == end) {
                return "";
            }
            return Pattern.quote(s.substring(start, end));
        }

        public boolean matchStrings(String str, @Nullable Map<String, String> uriTemplateVariables) {
            if (this.exactMatch) {
                return this.caseSensitive ? this.rawPattern.equals(str) : this.rawPattern.equalsIgnoreCase(str);
            } else if (this.pattern != null) {
                Matcher matcher = this.pattern.matcher(str);
                if (matcher.matches()) {
                    if (uriTemplateVariables != null) {
                        if (this.variableNames.size() != matcher.groupCount()) {
                            throw new IllegalArgumentException("The number of capturing groups in the pattern segment " +
                                    this.pattern + " does not match the number of URI template variables it defines, " +
                                    "which can occur if capturing groups are used in a URI template regex. " +
                                    "Use non-capturing groups instead.");
                        }
                        for (int i = 1; i <= matcher.groupCount(); i++) {
                            String name = this.variableNames.get(i - 1);
                            if (name.startsWith("*")) {
                                throw new IllegalArgumentException("Capturing patterns (" + name + ") are not " +
                                        "supported by the AntPathMatcher. Use the PathPatternParser instead.");
                            }
                            String value = matcher.group(i);
                            uriTemplateVariables.put(name, value);
                        }
                    }
                    return true;
                }
            }
            return false;
        }

    }


}

