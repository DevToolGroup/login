package group.devtool.component.login.core;

import group.devtool.component.login.core.exception.LoginRuntimeException;

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

/**
 * 登录服务工具类，主要用于构建登录过程中涉及的相关链接
 */
public final class RsaUtils {

  private RsaUtils() {

  }

  /**
   * RSA 签名
   * 
   * @param privateKey 私钥
   * @param applicationTicketId 应用登录凭证ID
   * @return 签名后的应用登录凭证ID
   */
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
      throw new LoginRuntimeException(e.getMessage());
    }
  }

  /**
   * 验证 RSA 签名是否合法
   * 
   * @param publicKey 公钥
   * @param applicationTicketId 应用登录凭证ID
   * @param applicationTicketSign 签名后的应用登录凭证ID
   * @return true：合法，false：不合法
   */
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
