package xyz.andw.anode.war;

public class Tower {

    public String guildTag;
    public String terrName;
    public long hp;
    public float def;
    public long dmgLo;
    public long dmgHi;
    public float atkSpeed;

    public int auraLevel;
    public int multiLevel; // TODO: add multi

    private long lastAura = 0; 
    private long auraDelaySum = 0; 
    private int auraCount = 0; 
    
    public Tower(String guildTag, String terrName, long hp, float def, long dmgLo, long dmgHi, float atkSpeed) {
        this.guildTag = guildTag;
        this.terrName = terrName;
        this.hp = hp;
        this.def = def;
        this.dmgLo = dmgLo;
        this.dmgHi = dmgHi;
        this.atkSpeed = atkSpeed;
    }

    public void didAura() {
        auraLevel = 1; // TODO: figure out a way to compute aura level from time later
        auraCount++;
        if (lastAura == 0) {
            lastAura = System.currentTimeMillis();
            return;
        }
        
        auraDelaySum += System.currentTimeMillis()-lastAura;
        lastAura = System.currentTimeMillis();
    }

    public float meanAuraDelay() {
        return auraDelaySum / auraCount;
    }

    public String toString() {
        return String.format("%s %s %dHP %.4f %d-%d %.4f", guildTag, terrName, hp, def ,dmgLo, dmgHi, atkSpeed);
    }

    // [SDU] Light Forest West Mid Tower - ❤ 300000 (10.0%) - ☠ 1000-1500 (0.5x)
    public static Tower fromBossString(String boss) {
        String[] tagTerrRest = boss.split(" - ");
        int tagTerrSpace = tagTerrRest[0].indexOf(" ");
        String targetTag = tagTerrRest[0].substring(0, tagTerrSpace).replace("[","").replace("]","");
        String targetTerr = tagTerrRest[0].substring(tagTerrSpace+1);
        String[] terrNameSplit = targetTerr.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < terrNameSplit.length-1; i++) {
            sb.append(terrNameSplit[i]); // get rid of the word tower in the name
            if (i != terrNameSplit.length-2) 
                sb.append(" ");
        }
        targetTerr = sb.toString();

        String[] _hpDef = tagTerrRest[1].split(" ");
        String hpString = _hpDef[1];
        Long hp = Long.parseLong(hpString);
        String defString = _hpDef[2];
        float def = Float.parseFloat(defString.substring(1, defString.length()-2));

        String[] _dmgSpd = tagTerrRest[2].split(" ");
        String dmgRange = _dmgSpd[1];
        int hyphenIdx = dmgRange.indexOf("-");
        int dmgLo = Integer.parseInt(dmgRange.substring(0, hyphenIdx));
        int dmgHi = Integer.parseInt(dmgRange.substring(hyphenIdx+1));
        String spdString = _dmgSpd[2];
        float atkSpeed = Float.parseFloat(spdString.substring(1, spdString.length()-2));

        return new Tower(targetTag, targetTerr, hp, def, dmgLo, dmgHi, atkSpeed);
    }
}
