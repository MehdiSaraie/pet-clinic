package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'b', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'c', def = "t1arr[2] != t2arr[2]")
@ClauseDefinition(clause = 'd', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'e', def = "t1arr[0] + t1arr[1] < t1arr[2]")
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	//predicate line 14
	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void sampleTest1() { //TFF
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(3, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void sampleTest2() { //FTF
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 4, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertFalse(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void sampleTest3() { //FFT
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a", //or "b" or "c"
		clause = 'a', //or 'b' or 'c'
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void sampleTest4() { //FFF
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertTrue(areCongruent);
	}

	//predicate line 15 (infeasible to satisfy CACC because TF is infeasible)
	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = false
	)
	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void sampleTest5() { //FF
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertTrue(areCongruent);
	}

	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		},
		predicateValue = true
	)
	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void sampleTest6() { //FT
		Triangle t1 = new Triangle(2, 3, 6);
		Triangle t2 = new Triangle(2, 3, 6);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertFalse(areCongruent);
	}

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void sampleTest7() { //TT
		Triangle t1 = new Triangle(-1, 0, 0);
		Triangle t2 = new Triangle(-1, 0, 0);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		assertFalse(areCongruent);
	}


	/*
	example:
		f = abcd + e, fbar = (abar + bbar + cbar + dbar)ebar = abar.ebar + bbar.ebar + cbar.ebar + dbar.ebar
	coverages are as following ("<--->" indicates matching between testcases):
				 UTPC            		  					CUTPNFP
	---------- implicant ------------   	-------------------clause -- implicant--
      f          abcd     	TTTTF	  <--->    TTTTF	UTP		 -		   abcd
	  fbar    abar.ebar     FTTTF	  <--->    FTTTF	NFP		 a		   abcd
	  fbar    bbar.ebar     TFTTF	  <--->    TFTTF	NFP		 b		   abcd
	  fbar    cbar.ebar     TTFTF	  <--->    TTFTF	NFP		 c		   abcd
	  fbar    dbar.ebar     TTTFF	  <--->    TTTFF	NFP		 d		   abcd
	  f           e			FFFFT
	  f           e			FFFTT
	  f           e			FFTFT
	  f           e			FFTTT
	  f           e			FTFFT
	  f           e			FTFTT
	  f           e			FTTFT
	  f           e			FTTTT	  <--->    FTTTT	UTP		 -			e
	  f           e			TFFFT
	  f           e			TFFTT
	  f           e			TFTFT
	  f           e			TFTTT
	  f           e			TTFFT
	  f           e			TTFTT
	  f           e			TTTFT
	  f           e			TTTTT

	  As we see above CUTPNFP is staisfied with 6 testcases but UTPC with 21 testcases. UTPC must contain every UTP
	   to cover e, but FTTTT is enough for CUTPNFP to cover 'e' as a UTP.
	  therefore CUTPNFP doesn't subsume UTPC.
	 */
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
		predicate = a && b && c && d || e;
		return predicate;
	}
}
