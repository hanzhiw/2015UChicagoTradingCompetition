package org.uchicago.options.utils;

import java.util.ArrayList;
import java.util.List;

public class InstrumentUtilities {
	
	/*
	 * 
	 */
	public static List<String> GLOBAL_SYMBOLS = new ArrayList<String>();
	public static List<String> MATH_SYMBOLS = new ArrayList<String>();	
	public static List<String> ARB_SYMBOLS = new ArrayList<String>();
	public static List<String> OPTIONS_SYMBOLS = new ArrayList<String>();
	
	public static List<String> PAIRS_UNDERLYINGS = new ArrayList<String>();	
	public static List<String> INDEX_UNDERLYINGS = new ArrayList<String>();
	public static List<String> OPTIONS_UNDERLYINGS = new ArrayList<String>();
	
	public static List<String> OPTIONS_OPTIONS = new ArrayList<String>();
	
	public static List<String> ALL_INSTRUMENTS = new ArrayList<String>();
	public static List<String> EMPTY_LIST = new ArrayList<String>();
	
	static {
		
		GLOBAL_SYMBOLS.add("SNOW-E");
		GLOBAL_SYMBOLS.add("ROBOT-E");
		
		MATH_SYMBOLS.add("MATH");
		ARB_SYMBOLS.add("ARB");
		
		
		PAIRS_UNDERLYINGS.add("MATH-E");
		INDEX_UNDERLYINGS.add("ARB-E");
		
		OPTIONS_SYMBOLS.add("OPT");
		OPTIONS_UNDERLYINGS.add("OPT-E");
		OPTIONS_OPTIONS.add("OPT-20150412-80C");
		OPTIONS_OPTIONS.add("OPT-20150412-90C");
		OPTIONS_OPTIONS.add("OPT-20150412-100C");
		OPTIONS_OPTIONS.add("OPT-20150412-110C");
		OPTIONS_OPTIONS.add("OPT-20150412-120C");

	}
	
	public static enum Case {	
		MATH(MATH_SYMBOLS, PAIRS_UNDERLYINGS, EMPTY_LIST),
		ARB(ARB_SYMBOLS, INDEX_UNDERLYINGS, EMPTY_LIST),
		OPTIONS(OPTIONS_SYMBOLS, OPTIONS_UNDERLYINGS, OPTIONS_OPTIONS);
		
		private List<String> symbolList;
		private List<String> underlyingList;
		private List<String> optionList;

		Case(List<String> symbolList, List<String> underlyingList, List<String> optionList) {
			this.symbolList = symbolList;
			this.underlyingList = underlyingList;
			this.optionList = optionList;
		}
		
		public List<String> getInstrumentList() {
			return underlyingList;
		}
		
		public List<String> getSymbolList() {
			return symbolList;
		}
		
		public List<String> getOptionList() {
			return symbolList;
		}
	}
	
	// ------------- All Instrument Methods -------------
	
	public static List<String> getAllInstrumentsForAllTeams() {
		List<String> instruments = new ArrayList<String>();
		for (Case caze : Case.values()) {
			instruments.addAll(getUnderlyingsForCase(caze));
			instruments.addAll(getOptionsForCase(caze));
		}
		instruments.addAll(GLOBAL_SYMBOLS);
		return instruments;
	}
	
	// -------------------- All teams, but case specific
	
	public static List<String> getUnderlyingsForCase(Case caze) {
		List<String> underlyings = new ArrayList<String>();
		for (String teamCode : TeamUtilities.TEAMS) {
			for (String underlying: caze.underlyingList) {
				underlyings.add(teamCode + "~" + underlying);
			}
		}
		return underlyings;
	}
	
	public static List<String> getOptionsForCase(Case caze) {
		List<String> options = new ArrayList<String>();
		for (String teamCode : TeamUtilities.TEAMS) {
			for (String option: caze.optionList) {
				options.add(teamCode + "~" + option);
			}
		}
		return options;
	}
	
	// -------------------- Team and case specific -----------
	
	public static List<String> getSymbolsForTeamByCase(Case caze, String teamCode) {
		List<String> symbols = new ArrayList<String>();
		if (TeamUtilities.validateTeamCode(teamCode)) {
			for (String symbol: caze.symbolList)
				symbols.add(teamCode + "~" + symbol);
		}
		else {
			throw new IllegalArgumentException("Unknown team code");
		}
		return symbols;
	}

}