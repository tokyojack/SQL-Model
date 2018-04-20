package com.valeon.core.commands.subcommands.playerStats;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
public class PlayerStat {

    private int totalKills;
    private int totalDeaths;

    public PlayerStat(int totalKills, int totalDeaths){
        this.totalKills = totalKills;
        this.totalDeaths = totalDeaths;
    }

    public PlayerStat(ResultSet resaultSet){
        try {
            this.totalKills = resaultSet.getInt("total_kills");
            this.totalDeaths = resaultSet.getInt("total_deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}