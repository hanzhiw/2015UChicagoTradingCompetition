package org.uchicago.options.utils;

public class TeamUtilities {
	
	public static final String[] TEAMS = new String[] { "CHI1", "CHI2", "CHI3", "CHI4", "CHI5", "CHI6", "MAR1",
		"ILL1", "ILL2", "ILL3", "ILL4", "ILL5", "BAR1", "DAR1", "IOW1", "CAL1", "CMU1", "CMU2", "CMU3",
		"NOR1", "MIT1", "MIT2"};
	
	public static boolean validateTeamCode(String teamCode) {
		for (String team : TEAMS) {
			if (team.equals(teamCode))
				return true;
		}
		return false;
	}

}