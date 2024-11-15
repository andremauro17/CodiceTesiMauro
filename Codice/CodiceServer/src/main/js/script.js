const BN = require('bn.js');
const elliptic = require('elliptic');
const { keccak256 } = require('ethers').utils;
const ec = new elliptic.ec('secp256k1');
const crypto = require('crypto');

// Funzione per generare la chiave pubblica
function generatePublicKey(privateKey) {
    const publicKey = ethers.utils.computePublicKey(privateKey, false);
    const publicKeyWithoutPrefix = publicKey.slice(2); // Rimuovi il '0x'

    // Prendi i primi 64 caratteri per X e i successivi 64 per Y
    const publicKeyX = "0x" + publicKeyWithoutPrefix.slice(0, 64);
    const publicKeyY = "0x" + publicKeyWithoutPrefix.slice(64, 128);

    return [publicKeyX, publicKeyY];  // Non aggiungere di nuovo "0x"
}

// Funzione per ottenere message2
function getMessageBytes32(message) {
    return ethers.utils.formatBytes32String(message);
}

// Funzione per calcolare c e s
function calculateCandS(privateKeyHex, message) {
    const key = ec.keyFromPrivate(privateKeyHex, 'hex');
    const messageBytes = Buffer.from(message, 'utf8');
    const H = hashToCurve(messageBytes);

    const gamma = H.mul(key.getPrivate());
    const kBytes = crypto.randomBytes(32);
    const k = new BN(kBytes).umod(ec.curve.n);
    const G = ec.g;
    const U = G.mul(k);
    const V = H.mul(k);
    const c = calculateChallenge(G, H, gamma, U, V);
    const s = k.add(c.mul(key.getPrivate())).umod(ec.curve.n);

    return { c: c.toString(10), s: s.toString(10) };
}

// Funzioni di supporto
function hashToCurve(messageBytes) {
    let count = 0;
    while (true) {
        const ctrBuffer = Buffer.alloc(4);
        ctrBuffer.writeUInt32BE(count);
        const hashInput = Buffer.concat([messageBytes, ctrBuffer]);
        const trialHash = keccak256(hashInput);
        const trialHashBN = new BN(trialHash.slice(2), 16);
        try {
            const point = ec.curve.pointFromX(trialHashBN, false);
            if (point.validate()) {
                return point;
            }
        } catch (e) {}
        count++;
    }
}

function calculateChallenge(G, H, gamma, U, V) {
    const GBytes = encodePoint(G);
    const HBytes = encodePoint(H);
    const gammaBytes = encodePoint(gamma);
    const UBytes = encodePoint(U);
    const VBytes = encodePoint(V);
    const cHashInput = Buffer.concat([GBytes, HBytes, gammaBytes, UBytes, VBytes]);
    const cHash = keccak256(cHashInput);
    return new BN(cHash.slice(2), 16).umod(ec.curve.n);
}

function encodePoint(point) {
    const x = point.getX().toArrayLike(Buffer, 'be', 32);
    const y = point.getY().toArrayLike(Buffer, 'be', 32);
    return Buffer.concat([x, y]);
}

module.exports = { generatePublicKey, getMessageBytes32, calculateCandS };
