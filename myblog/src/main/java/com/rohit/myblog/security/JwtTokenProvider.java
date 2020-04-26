package com.rohit.myblog.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.rohit.myblog.exceptions.BlogException;
import com.rohit.myblog.exceptions.Error;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Service
public class JwtTokenProvider {

	public static final Logger LOG = LogManager.getLogger(JwtTokenProvider.class);

	private KeyStore keyStore;

	private static String ALIAS;
	private static String SECRET;
	private static long expireAfterMin=60;

	@Autowired
	private Environment env;

	@PostConstruct
	public void init() throws BlogException {
		try {
			ALIAS = env.getProperty("keystore.alias");
			SECRET = env.getProperty("keystore.secret");
			expireAfterMin=Long.parseLong(env.getProperty("token.expireInMin"));
			keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getResourceAsStream(env.getProperty("keystore.file"));
			if(inputStream!=null)
				keyStore.load(inputStream, SECRET.toCharArray());
			else {
				LOG.error("Keystore not found");
				throw new BlogException(Error.FAILED_TO_LOAD_KEYSTORE,null);
			}
		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException  e) {
			LOG.error("Failed to load keydtore", e);
			throw new BlogException(Error.FAILED_TO_LOAD_KEYSTORE, e);
		}
	}

	public String generateToken(Authentication authentication) throws BlogException {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).setExpiration(new Date(System.currentTimeMillis() + expireAfterMin  * 60L * 1000L)).compact();
	}

	private PrivateKey getPrivateKey() throws BlogException {
		try {
			return (PrivateKey) keyStore.getKey(ALIAS, SECRET.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			LOG.error("Failed to get private key from keystore", e);
			throw new BlogException(Error.FAILED_TO_LOAD_KEY, e);
		}
	}

	private PublicKey getPublicKey() throws BlogException {
		try {
			return (PublicKey) keyStore.getCertificate(ALIAS).getPublicKey();
		} catch (KeyStoreException e) {
			LOG.error("Failed to get public key from keystore", e);
			throw new BlogException(Error.FAILED_TO_LOAD_KEY, e);
		}
	}

	public boolean validateToken(String jwt) {
		try {
			Jwts.parser().setSigningKey(getPublicKey()).parse(jwt);
			return true;
		} catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
			LOG.error("token is invalid");
			return false;
		} catch (Exception e) {
			LOG.error("Unexpected error ",e);
			return false;
		}
	}

	public String getUsernameFromToken(String token) throws BlogException {
		Claims claims = Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

}
