package application.controller;

import application.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {



    @Test
    void createDestillat() {
    //Tester oprettelsen af destilliat, med korrekt alkoholprocent.

        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB001", LocalDate.of(2020,1,1),300,maltList);
        maltbatch.createMalt("Byg","Mark 1",20.0);


        Destillat destillat = Controller.createDestillat("Destillat1", LocalDate.of(2020,1,1), LocalDate.of(2020,1,2), 50.0, 60.0, false, 200.0, maltbatch);

        assertNotNull(destillat);
        assertEquals("Destillat1", destillat.getDestillatID());
        assertEquals(60,destillat.getAlkoholProcent());
        assertEquals(1,maltbatch.getMalt().size());
        assertEquals("Byg",maltbatch.getMalt().get(0).getKornSort());

    }

    @Test
    void createDestillatForkert() {
        ////Tester oprettelsen af destilliat, med forkert alkoholprocent.
        ArrayList<Malt> maltList = new ArrayList<>();
        MaltBatch maltbatch = Controller.createMaltBatch("MB002",LocalDate.of(2020,1,1),20,maltList);

    assertThrows(IllegalArgumentException.class,() ->{
        Controller.createDestillat("ForkertDestiliat",LocalDate.of(2020,2,1), LocalDate.of(2020,2,2),50,59.0,false,200,maltbatch);
    });
    }

    @Test
    void createFadKorrekt() {
        Fad fad = Controller.createFad(1,200,"Eg","FadAPS",1, null,null,null);

        assertNotNull(fad);
        assertEquals(1,fad.getFadID());
        assertEquals(200,fad.getFadILiter());
        assertEquals("Eg",fad.getMateriale());
        assertEquals("FadAPS",fad.getLeverandør());
        assertEquals(1,fad.getAntalGangeBrugt());



    }

    @Test
    void createFadForkert() {
        assertThrows(IllegalArgumentException.class, () -> {
            Controller.createFad(0, 200, "Eg", "FadAPS", 1, null, null, null);
        });


    }

    @Test
    void createMaltBatch() {

        ArrayList<Malt> maltList = new ArrayList<>();

       MaltBatch maltBatch = Controller.createMaltBatch("MB001",LocalDate.of(2020,1,1),300,maltList);

       assertNotNull(maltBatch);
       assertEquals("MB001", maltBatch.getBatchNummer());
       assertEquals(LocalDate.of(2020,1,1), maltBatch.getDato());
       assertEquals(300,maltBatch.getMængde());
       assertEquals(1,maltBatch.getMalt().size());

    }

    @Test
    void createPåfyldning() {
    }

    @Test
    void createTapning() {
    }

    @Test
    void createWhisky() {
    }

    @Test
    void createLager() {
    }

    @Test
    void createFadType() {
    }
}