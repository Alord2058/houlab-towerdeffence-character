package jp.houlab.alord2058.character.blender.Ultimate;

import org.bukkit.Location;
import org.bukkit.entity.TextDisplay;

public class SaveTextDisplaySData {

    TextDisplay textDisplay;
    Location warpLocationS;
    double warpXS;
    double warpYS;
    double warpZS;
    double vx;
    double vz;

    //getter

    public TextDisplay getTextDisplay() {
        return textDisplay;
    }

    public Location getWarpLocationS() {
        return warpLocationS;
    }

    public double getWarpXS() {
        return warpXS;
    }

    public double getWarpYS() {
        return warpYS;
    }

    public double getWarpZS() {
        return warpZS;
    }

    public double getVx() {
        return vx;
    }

    public double getVz() {
        return vz;
    }

    //setter

    public void setTextDisplay(TextDisplay textDisplay) {
        this.textDisplay = textDisplay;
    }

    public void setWarpLocationS(Location warpLocationS) {
        this.warpLocationS = warpLocationS;
    }

    public void setWarpXS(double warpXS) {
        this.warpXS = warpXS;
    }

    public void setWarpYS(double warpYS) {
        this.warpYS = warpYS;
    }

    public void setWarpZS(double warpZS) {
        this.warpZS = warpZS;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVz(double vz) {
        this.vz = vz;
    }
}
