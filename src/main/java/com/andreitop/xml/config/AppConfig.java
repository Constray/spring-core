package com.andreitop.xml.config;

import com.andreitop.xml.mount.Mount;
import com.andreitop.xml.mount.Tiger;
import com.andreitop.xml.mount.Wolf;
import com.andreitop.xml.unit.Human;
import com.andreitop.xml.unit.Orc;
import com.andreitop.xml.unit.Troll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@ComponentScan(basePackages = "com.andreitop.xml")
@PropertySource("classpath:config/heroes.properties")
public class AppConfig {

    @Value("${character.created}")
    private String CHARACTER_CREATION_DATE;

    private static final String FURY_AXE = "furyAxe";
    private static final String THUNDER_FURY = "thunderFury";
    private static final String SOUL_BLADE = "soulBlade";
    private static final int THRALL_COLOR_CODE = 9;

    @Bean
    public SimpleDateFormat dateFormatter() {
        return new SimpleDateFormat("dd-mm-yyyy");
    }

    @Bean
    public Mount frostWolf(){
        return new Wolf();
    }

    @Bean
    public Mount shadowTiger(){
        return new Tiger();
    }

    @Bean
    public HashMap<String, Mount> trollMountMap() {
        HashMap<String, Mount> trollMountMap = new HashMap<>();
        trollMountMap.put("m1", frostWolf());
        trollMountMap.put("m2", shadowTiger());
        return trollMountMap;
    }

    @Bean
    public HashSet<Mount> trollMountSet() {
        HashSet<Mount> trollMountSet = new LinkedHashSet<>();
        trollMountSet.add(shadowTiger());
        trollMountSet.add(frostWolf());
        return trollMountSet;
    }

    @Bean
    public Troll zulJin() throws ParseException {
        Troll zulJin = new Troll();
        zulJin.setCreationDate(dateFormatter().parse(CHARACTER_CREATION_DATE));
        zulJin.setColorCode (java.util.concurrent.ThreadLocalRandom.current().nextInt(1,10));
        List<Mount> listOfMounts = new ArrayList<>();
        listOfMounts.add(Troll.DEFAULT_MOUNT);
        listOfMounts.add(null);
        listOfMounts.add(shadowTiger());
        zulJin.setListOfMounts(listOfMounts);
        zulJin.setMapOfMounts(trollMountMap());
        zulJin.setSetOfMounts(trollMountSet());
        return zulJin;
    }

    @Bean
    public Orc thrall() {
        Orc thrall = new Orc(frostWolf());
        thrall.setWeapon(FURY_AXE);
        thrall.setColorCode(THRALL_COLOR_CODE);
        return thrall;
    }

    @Bean
    public Human knight() {
       return new Human(shadowTiger(), THUNDER_FURY, SOUL_BLADE);
    }
}
