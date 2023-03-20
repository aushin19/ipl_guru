package com.onemosys.ipl.Modals;

public class TeamScheduleModal {
    public String team1_name, team2_name;
    public String matchTime;
    public String matchVenue;
    public boolean isTeamAvailable;

    public TeamScheduleModal(){}

    public TeamScheduleModal(String team1_name, String team2_name, String matchTime, boolean isTeamAvailable ,String matchVenue) {
        this.team1_name = team1_name;
        this.team2_name = team2_name;
        this.matchTime = matchTime;
        this.isTeamAvailable = isTeamAvailable;
        this.matchVenue = matchVenue;
    }
}
