package org.example;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class VRF {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static class VRFOutput {
        public byte[] vrfOutput;
        public byte[] proof;

        public VRFOutput(byte[] vrfOutput, byte[] proof) {
            this.vrfOutput = vrfOutput;
            this.proof = proof;
        }

        public VRFOutput() {
            this.vrfOutput = new byte[0];
            this.proof = new byte[0];
        }
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }


    public static VRFOutput generateVRF(PrivateKey privateKey, byte[] message) throws Exception {
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(message);
        byte[] signature = ecdsaSign.sign();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] vrfOutput = digest.digest(signature);
        return new VRFOutput(vrfOutput, signature);
    }


    public static boolean verifyVRF(PublicKey publicKey, byte[] message, VRFOutput vrf) throws Exception {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(message);
        boolean isProofValid = ecdsaVerify.verify(vrf.proof);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] recreatedVrfOutput = digest.digest(vrf.proof);
        boolean isOutputValid = MessageDigest.isEqual(recreatedVrfOutput, vrf.vrfOutput);
        return isProofValid && isOutputValid;
    }
}
