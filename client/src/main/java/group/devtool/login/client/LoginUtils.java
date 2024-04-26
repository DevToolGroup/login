package group.devtool.login.client;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class LoginUtils {

  private static final String Q = "?";

  private static final String EQ = "=";

  private static final String LINK = "&";

  private static final String RedirectUrl = "redirectUrl";

  private static final String AppId = "appid";

  private static final String ApplicationTicketId = "appTicket";

  private static final String ApplicationTicketSign = "signTicket";

  private static final String TokenId = "tokenId";

  public static String resolveAuthenticateRequestUrl(String authenticateUrl) {
    Map<String, String> params = getQueryMap(authenticateUrl);
    return params.get(RedirectUrl);
  }

  public static String redirectAuthorizeUrl(String authorizeUrl, String requestUrl) {
    return new StringBuilder()
        .append(authorizeUrl)
        .append(Q)
        .append(RedirectUrl)
        .append(EQ)
        .append(URLEncoder.encode(requestUrl, StandardCharsets.UTF_8))
        .toString();
  }

  public static String constructValidateTicketUrl(String validateUrl, String applicationTicketId,
      String privateKey) {
    return new StringBuilder()
        .append(validateUrl)
        .append(Q)
        .append(ApplicationTicketId)
        .append(EQ)
        .append(URLEncoder.encode(applicationTicketId, StandardCharsets.UTF_8))
        .append(LINK)
        .append(ApplicationTicketSign)
        .append(EQ)
        .append(URLEncoder.encode(sign(privateKey, applicationTicketId), StandardCharsets.UTF_8))
        .toString();
  }

  public static String resolveValidateTicket(String url) {
    Map<String, String> params = getQueryMap(url);
    return params.get(ApplicationTicketId);
  }

  public static String resolveValidateSign(String url) {
    Map<String, String> params = getQueryMap(url);
    return params.get(ApplicationTicketSign);
  }

  public static String redirectApplicationTicketUrl(String originUrl, String applicationTicketId) {
    if (null == applicationTicketId) {
      return originUrl;
    }
    URI uri = URI.create(originUrl);
    String link;
    if (null == uri.getQuery()) {
      link = Q;
    } else {
      link = LINK;
    }
    return new StringBuilder()
        .append(originUrl)
        .append(link)
        .append(ApplicationTicketId)
        .append(EQ)
        .append(URLEncoder.encode(applicationTicketId, StandardCharsets.UTF_8))
        .toString();
  }

  public static String constructServerLogoutUrl(String serverLogoutUrl, String requestUrl) {
    return new StringBuilder()
        .append(serverLogoutUrl)
        .append(Q)
        .append(RedirectUrl)
        .append(EQ)
        .append(URLEncoder.encode(requestUrl, StandardCharsets.UTF_8))
        .toString();
  }

  public static String constructClientLogoutUrl(String logoutUrl, String applicationTicketId) {
    return new StringBuilder()
        .append(logoutUrl)
        .append(Q)
        .append(TokenId)
        .append(EQ)
        .append(URLEncoder.encode(applicationTicketId, StandardCharsets.UTF_8))
        .toString();
  }

  public static String resolveClientLogoutSessionId(String url) {
    Map<String, String> parameters = getQueryMap(url);
    return parameters.get(TokenId);
  }

  public static String getQuery(String url) {
    URI uri = URI.create(url);
    String query = uri.getRawQuery();
    return query;
  }

  private static Map<String, String> getQueryMap(String authorizeUrl) {
    String query = getQuery(authorizeUrl);
    return getQueryParameters(query);
  }

  private static Map<String, String> getQueryParameters(String query) {
    Map<String, String> params = new HashMap<>();

    if (null == query) {
      return params;
    }

    try {
      for (String param : query.split("&")) {
        String[] entry = param.split("=");
        if (entry.length > 1) {
          params.put(URLDecoder.decode(entry[0], "UTF-8"), URLDecoder.decode(entry[1], "UTF-8"));
        }
      }
    } catch (UnsupportedEncodingException exception) {
      throw new IllegalArgumentException(exception.getMessage(), exception);
    }
    return params;
  }

  public static String sign(String privateKey, String applicationTicketId) {
    // 使用SHA256withRSA算法进行签名
    byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
    KeyFactory keyFactory;
    try {
      keyFactory = KeyFactory.getInstance("RSA");
      Signature signature = Signature.getInstance("SHA256withRSA");
      PrivateKey priKey = keyFactory.generatePrivate(keySpec);
      signature.initSign(priKey);
      signature.update(applicationTicketId.getBytes());

      // 生成签名
      byte[] digitalSignature = signature.sign();
      return Base64.getEncoder().encodeToString(digitalSignature);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
      // TODO log
      throw new LoginUnknownException(e.getMessage());
    }
  }

  public static boolean verify(String publicKey, String applicationTicketId, String applicationTicketSign) {
    // 解码公钥
    byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
    KeyFactory keyFactory;
    try {
      keyFactory = KeyFactory.getInstance("RSA");
      PublicKey pubKey = keyFactory.generatePublic(keySpec);

      // 实例化签名对象
      Signature sig = Signature.getInstance("SHA256withRSA");
      sig.initVerify(pubKey);

      // 更新要验证的数据
      sig.update(applicationTicketId.getBytes());

      // 验证签名
      return sig.verify(Base64.getDecoder().decode(applicationTicketSign));

    } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
      // TODO log
      return false;
    }
  }

}
