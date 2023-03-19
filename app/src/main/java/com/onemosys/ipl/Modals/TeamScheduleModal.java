package com.onemosys.ipl.Modals;

public class TeamScheduleModal {
    public String team1_name, team1_short_name, team2_name, team2_short_name;
    public String matchDay;
    public String matchTime;
    public boolean isTeamAvailable;

    public TeamScheduleModal(){}

    public TeamScheduleModal(String team1_name, String team1_short_name, String team2_name, String team2_short_name, String matchDay, String matchTime, boolean isTeamAvailable) {
        this.team1_name = team1_name;
        this.team1_short_name = team1_short_name;
        this.team2_name = team2_name;
        this.team2_short_name = team2_short_name;
        this.matchDay = matchDay;
        this.matchTime = matchTime;
        this.isTeamAvailable = isTeamAvailable;
    }
}
