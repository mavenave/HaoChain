package com.hao.HaoChain.core

import java.io.File
import java.security._
import java.security.spec.{ECPublicKeySpec, KeySpec, PKCS8EncodedKeySpec, X509EncodedKeySpec}
import java.util.Base64
import javax.crypto.spec.SecretKeySpec
import java.security.KeyFactory
import java.security.PublicKey

object StringUtils {
  def sha256(string: String): String = {
    val byteArray: Array[Byte] = MessageDigest.getInstance("SHA-256").digest(string.getBytes("UTF-8"))
    val hexString = new StringBuffer
    // This will contain hash as hexidecimal
    var i = 0
    while ( {
      i < byteArray.length
    }) {
      val hex = Integer.toHexString(0xff & byteArray(i))
      if (hex.length == 1) hexString.append('0')
      hexString.append(hex)

      {
        i += 1;
        i - 1
      }
    }
    return hexString.toString
  }

  def concatPath(path1: String, path2: String): String = {
    return new File(path1, path2).toString
  }

  def getStringFromKey(key: Key): String = {
    return Base64.getEncoder.encodeToString(key.getEncoded)
  }

  def getPublicKeyFromString(string: String): PublicKey = {
    val bytes = Base64.getDecoder.decode(string)
    val keyFactory = KeyFactory.getInstance("ECDSA")
    val keySpec = new X509EncodedKeySpec(bytes)
    return keyFactory.generatePublic(keySpec)
  }

  def getPrivateKeyFromString(string: String): PrivateKey = {
    val bytes = Base64.getDecoder.decode(string)
    val keyFactory = KeyFactory.getInstance("ECDSA")
    val keySpec = new PKCS8EncodedKeySpec(bytes)
    return keyFactory.generatePrivate(keySpec)
  }

  def applyECDSASig(privateKey: PrivateKey, input: String): Array[Byte] = {
    var dsa: Signature = null
    var output = new Array[Byte](0)
    try {
      dsa = Signature.getInstance("ECDSA", "BC")
      dsa.initSign(privateKey)
      val strByte = input.getBytes
      dsa.update(strByte)
      val realSig = dsa.sign
      output = realSig
    } catch {
      case e: Exception =>
        throw new RuntimeException(e)
    }
    return output
  }

  def verifyECDSASig(publicKey: PublicKey, data: String, signature: Array[Byte]): Boolean = try {
    val ecdsaVerify = Signature.getInstance("ECDSA", "BC")
    ecdsaVerify.initVerify(publicKey)
    ecdsaVerify.update(data.getBytes)
    return ecdsaVerify.verify(signature)
  } catch {
    case e: Exception =>
      throw new RuntimeException(e)
  }
}
