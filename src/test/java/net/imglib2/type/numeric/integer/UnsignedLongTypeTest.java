/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2015 Tobias Pietzsch, Stephan Preibisch, Barry DeZonia,
 * Stephan Saalfeld, Curtis Rueden, Albert Cardona, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Jonathan Hale, Lee Kamentsky, Larry Lindsey, Mark
 * Hiner, Michael Zinsmaier, Martin Horn, Grant Harris, Aivar Grislis, John
 * Bogovic, Steffen Jaensch, Stefan Helfrich, Jan Funke, Nick Perry, Mark Longair,
 * Melissa Linkert and Dimiter Prodanov.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package net.imglib2.type.numeric.integer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.junit.Test;


public class UnsignedLongTypeTest {

	private UnsignedLongType u = new UnsignedLongType();
	private UnsignedLongType t = new UnsignedLongType();

	/** Tests that {@link UnsignedLongType#compareTo(UnsignedLongType)} works for
	 * comparing a positive number to a negative number and vice versa.
	 */
	@Test
	public void testComparePosNeg(){

		u.set( -1L );
		t.set( 1L );
		assertTrue( u.compareTo( t ) >= 1 );

		u.set( 9223372036854775807L );
		t.set( -9223372036854775808L );
		assertTrue( u.compareTo( t ) <= -1 );

		u.set( -109817491384701984L );
		t.set( 12L );
		assertTrue( u.compareTo( t ) >= 1 );

	}

	/** Tests that {@link UnsignedLongType#compareTo(UnsignedLongType)} works for
	 * comparing two negative numbers.
	 */
	@Test
	public void testCompareNegatives(){

		u.set( -9000L );
		t.set( -9000L );
		assertEquals( u.compareTo( t ), 0 );

		u.set( -16L );
		t.set( -10984012840123984L );
		assertTrue( u.compareTo( t ) >= 1 );

		u.set( -500L );
		t.set( -219L );
		assertTrue( u.compareTo( t ) <= -1 );

	}

	/** Tests that {@link UnsignedLongType#compareTo(UnsignedLongType)} works for
	 * comparing two positive numbers.
	 */
	@Test
	public void testComparePositives(){

		u.set( 100L );
		t.set( 100L );
		assertEquals( u.compareTo( t ), 0);

		u.set( 3098080948019L );
		t.set( 1L );
		assertTrue( u.compareTo( t ) >= 1 );

		u.set( 199L );
		t.set( 299L );
		assertTrue( u.compareTo( t ) <= -1 );

	}

	/** Tests that {@link UnsignedLongType#compareTo(UnsignedLongType)} works
	 * when comparing values to zero.
	 */
	@Test
	public void testCompareZero() {

		u.set( 0L );
		t.set( 0L );
		assertEquals( u.compareTo( t ), 0 );

		u.set( -17112921L );
		t.set( 0L );
		assertTrue( u.compareTo( t ) >= 1 );

		u.set( 0L );
		t.set( 698L );
		assertTrue( u.compareTo( t ) <= -1 );

	}

	/**
	 * Tests {@link UnsignedLongType#UnsignedLongType(BigInteger)} works for out
	 * of range values.
	 */
	@Test
	public void testBIConstructor() {

		final BigInteger bi = new BigInteger( "ABCD14984904EFEFEFE4324904294D17A", 16 );
		final UnsignedLongType l = new UnsignedLongType( bi );

		assertEquals( bi.longValue(), l.get() );
	}

	/**
	 * Tests that {@link UnsignedLongType#getBigInteger()} returns the unsigned
	 * representation of an {@link UnsignedLongType} regardless of if it was
	 * constructed with a {@code long} or a {@code BigInteger}.
	 */
	@Test
	public void testGetBigInteger() {

		final BigInteger mask = new BigInteger( "FFFFFFFFFFFFFFFF", 16 );
		final BigInteger bi = new BigInteger( "DEAD12345678BEEF", 16 );
		final UnsignedLongType l = new UnsignedLongType( bi );

		assertEquals( bi.and( mask ), l.getBigInteger() );

		final UnsignedLongType l2 = new UnsignedLongType( -473194873871904l );

		assertEquals(BigInteger.valueOf( -473194873871904l ).and( mask ),
			l2.getBigInteger() );
	}

	/**
	 * Tests that {@link UnsignedLongType#setBigInteger(BigInteger)} works and
	 * can still return the proper long value.
	 */
	@Test
	public void testSetBigInteger() {

		final long l = -184713894790123847l;
		final UnsignedLongType ul = new UnsignedLongType( l );

		assertEquals( ul.get(), l );

		final BigInteger bi = new BigInteger( "AAAAAA3141343BBBBBBBBBBB4134", 16 );
		ul.setBigInteger( bi );

		assertEquals( ul.get(), bi.longValue() );
	}
}
