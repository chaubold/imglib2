/**
 * Copyright (c) 2009--2013, ImgLib2 developers
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.  Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution.  Neither the name of the imglib project nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.imglib2.realtransform;

import net.imglib2.RealLocalizable;
import net.imglib2.RealPoint;
import net.imglib2.RealPositionable;

/**
 * <em>n</em>-d arbitrary scaling.
 *
 * @author Stephan Saalfeld <saalfeld@mpi-cbg.de>
 */
public class Scale implements AffineGet
{
	final protected double[] s;
	final protected Scale inverse;
	final protected RealPoint[] ds;
	
	protected Scale( final double[] s, final Scale inverse, final RealPoint[] ds )
	{
		this.s = s;
		this.ds = ds;
		this.inverse = inverse;
	}
	
	public Scale( final double... s )
	{
		this.s = s.clone();
		ds = new RealPoint[ s.length ];
		final double[] si = new double[ s.length ];
		for ( int d =0; d < s.length; ++d )
		{
			si[ d ] = 1.0 / s[ d ];
			final RealPoint dd = new RealPoint( s.length );
			dd.setPosition( s[ d ], d );
		}
		inverse = new Scale( si, this, ds );
	}
	
	@Override
	public void applyInverse( final double[] source, final double[] target )
	{
		assert source.length >= s.length && target.length >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			source[ d ] = target[ d ] / s[ d ];
	}

	@Override
	public void applyInverse( final float[] source, final float[] target )
	{
		assert source.length >= s.length && target.length >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			source[ d ] = ( float )( target[ d ] / s[ d ] );
		
	}

	@Override
	public void applyInverse( final RealPositionable source, final RealLocalizable target )
	{
		assert source.numDimensions() >= s.length && target.numDimensions() >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			source.setPosition( target.getDoublePosition( d ) / s[ d ], d );
	}

	@Override
	public Scale inverse()
	{
		return inverse;
	}

	@Override
	public int numSourceDimensions()
	{
		return s.length;
	}

	@Override
	public int numTargetDimensions()
	{
		return s.length;
	}

	@Override
	public void apply( final double[] source, final double[] target )
	{
		assert source.length >= s.length && target.length >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			target[ d ] = source[ d ] * s[ d ];
	}

	@Override
	public void apply( final float[] source, final float[] target )
	{
		assert source.length >= s.length && target.length >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			target[ d ] = ( float )( source[ d ] * s[ d ] );
	}

	@Override
	public void apply( final RealLocalizable source, final RealPositionable target )
	{
		assert source.numDimensions() >= s.length && target.numDimensions() >= s.length : "Input dimensions too small.";
		
		for ( int d =0; d < s.length; ++d )
			target.setPosition( source.getDoublePosition( d ) / s[ d ], d );
	}

	@Override
	public double get( final int row, final int column )
	{
		return row == column ? s[ row ] : 0;
	}

	@Override
	public double[] getRowPackedCopy()
	{
		final int step = s.length + 2;
		final double[] matrix = new double[ s.length * s.length + s.length ];
		for ( int d = 0; d < s.length; ++d )
			matrix[ d * step ] = s[ d ];
		return matrix;
	}

	@Override
	public RealLocalizable d( final int d )
	{
		return ds[ d ];
	}

	@Override
	public AffineGet inverseAffine()
	{
		return inverse();
	}
	
}