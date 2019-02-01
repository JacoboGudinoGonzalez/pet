package com.mx.nj.pet.util;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mx.nj.pet.model.Usuario;
import com.mx.nj.pet.security.UpdatableBCrypt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class Util {

	static UpdatableBCrypt oldHasher = new UpdatableBCrypt(7);
	
	static String[] mutableHash = new String[1];
	public static Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

	public static String hash(String password) {
	    return oldHasher.hash(password);
	}

	public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
	    return oldHasher.verifyAndUpdateHash(password, hash, updateFunc);
	}
	
	public static Boolean parseToken(String token) {
		try {
			String KEY = "token_petNJ";
			Claims body = Jwts.parser()
					.setSigningKey(KEY)
					.parseClaimsJws(token)
					.getBody();

			Usuario u = new Usuario();
			u.setName(body.getSubject());
			u.setEmail((String) body.get("email"));

			return true;

		} catch (JwtException e) {
			return false;
		} catch (ClassCastException e){
			return false;
		}
	}
	
	public static Usuario parseTokenToUser(String token) {
		Usuario u = null;
		try {
			String KEY = "token_petNJ";
			Claims body = Jwts.parser()
					.setSigningKey(KEY)
					.parseClaimsJws(token)
					.getBody();

			u = new Usuario();
			u.setId((Integer)body.get("id"));
			u.setName(body.getSubject());
			u.setEmail((String) body.get("email"));

			return u;

		} catch (JwtException e) {
			return u;
		} catch (ClassCastException e){
			return u;
		}
	}
	
	public static  <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size))
                .values();
    }
	
	public static Date getDate() {
		return new java.sql.Date(Calendar.getInstance().getTime().getTime());
	}
}