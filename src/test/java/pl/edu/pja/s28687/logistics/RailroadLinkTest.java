package pl.edu.pja.s28687.logistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pl.edu.pja.s28687.Locomotive;
import pl.edu.pja.s28687.TrainSet;
import pl.edu.pja.s28687.TrainStation;
import pl.edu.pja.s28687.factories.CarsFactory;
import pl.edu.pja.s28687.factories.LocomotiveFactory;
import pl.edu.pja.s28687.factories.TrainSetFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class RailroadLinkTest {
    private static final TrainStation station1 = new TrainStation("Station1", 0, 0);
    private static final TrainStation station2 = new TrainStation("Station2", 100, 100);

    private RailroadLink link;

    private static final LocoBase locoBase = new LocoBase();
    private static final LocomotiveFactory locoFactory = new LocomotiveFactory(locoBase);

    private List<Locomotive> locomotives = new ArrayList<>();
    private List<MockTrain> mockTrains = new ArrayList<>();

    static class MockTrain extends Thread{
        private final int id;
        private final RailroadLink link;
        private final Locomotive locomotive;
        private boolean leave = false;

        MockTrain(int id, RailroadLink link, Locomotive locomotive){
            this.id = id;
            this.link = link;
            this.locomotive = locomotive;
        }

        public void setLeave(boolean leave) {
            this.leave = true;
        }

        @Override
        public void run() {
            try {
                link.enterRailway(locomotive);
                while (!leave) {
                    Thread.sleep(1);
                }
                link.leaveRailway(locomotive);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @BeforeEach
    void setUp() {
        link = new RailroadLink(station1, station2);
        locomotives = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Locomotive l = locoFactory.createRandomLocomotive();
            locomotives.add(l);
            MockTrain train = new MockTrain(i, link, l);
            mockTrains.add(train);
        }
    }

    @Test
    void getWaitingLocomotives_WhenNoLocomotivesAccessingRailway_ShouldReturnEmptyList() {
        assertEquals(Collections.emptyList(), link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenOneLocomotiveAccessingRailway_ShouldReturnEmptyList() throws InterruptedException {
        Locomotive l = locoFactory.createRandomLocomotive();
        MockTrain train = new MockTrain(0, link, l);
        train.start();
        Thread.sleep(5);
        assertEquals(Collections.emptyList(), link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenTwoLocomotivesAccessingRailway_ShouldReturnOneLocomotive() throws InterruptedException {
        Locomotive l1 = locoFactory.createRandomLocomotive();
        MockTrain train1 = new MockTrain(0, link, l1);
        train1.start();
        Thread.sleep(5);
        Locomotive l2 = locoFactory.createRandomLocomotive();
        MockTrain train2 = new MockTrain(1, link, l2);
        train2.start();
        Thread.sleep(5);
        assertEquals(1, link.getWaitingLocomotives().size());
        assertEquals(l2, link.getWaitingLocomotives().get(0));
    }

    @Test
    void getWaitingLocomotives_WhenTenLocomotivesAccessingRailway_ShouldReturnNineLocomotives() throws InterruptedException {
        for (MockTrain mockTrain : mockTrains) {
            mockTrain.start();
            Thread.sleep(10);
        }
        List<Locomotive> expectedWaitingLocomotives = locomotives.subList(1, locomotives.size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenTenLocomotivesAccessingRailwayAndOneLeaves_ShouldReturnEightLocomotives() throws InterruptedException {
        for (MockTrain mockTrain : mockTrains) {
            mockTrain.start();
            Thread.sleep(10);
        }
        List<Locomotive> expectedWaitingLocomotives = locomotives.subList(1, locomotives.size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
        mockTrains.get(0).setLeave(true);
        Thread.sleep(10);
        expectedWaitingLocomotives = expectedWaitingLocomotives.subList(1, expectedWaitingLocomotives.size());
        assertEquals(8, link.getWaitingLocomotives().size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenTenLocomotivesAccessingRailwayAndTwoLeave_ShouldReturnSevenLocomotives() throws InterruptedException {
        for (MockTrain mockTrain : mockTrains) {
            mockTrain.start();
            Thread.sleep(10);
        }
        List<Locomotive> expectedWaitingLocomotives = locomotives.subList(1, locomotives.size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
        mockTrains.get(0).setLeave(true);
        Thread.sleep(10);
        expectedWaitingLocomotives = expectedWaitingLocomotives.subList(1, expectedWaitingLocomotives.size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
        mockTrains.get(1).setLeave(true);
        Thread.sleep(10);
        expectedWaitingLocomotives = expectedWaitingLocomotives.subList(1, expectedWaitingLocomotives.size());
        assertEquals(7, link.getWaitingLocomotives().size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenTenLocomotivesAccessingRailwayAndAllLeave_ShouldReturnEmptyList() throws InterruptedException {
        for (MockTrain mockTrain : mockTrains) {
            mockTrain.start();
            Thread.sleep(10);
        }
        List<Locomotive> expectedWaitingLocomotives = locomotives.subList(1, locomotives.size());
        assertEquals(expectedWaitingLocomotives, link.getWaitingLocomotives());
        for (MockTrain mockTrain : mockTrains) {
            mockTrain.setLeave(true);
        }
        Thread.sleep(10);
        assertEquals(Collections.emptyList(), link.getWaitingLocomotives());
    }

    @Test
    void getWaitingLocomotives_WhenTwoLocomotivesAccessingRailway_ShouldReturnListWithOne() throws InterruptedException {
        // second test to make sure if setting mock train 2 (waiting one) to leave will not affect the list
        Locomotive l1 = locoFactory.createRandomLocomotive();
        MockTrain train1 = new MockTrain(0, link, l1);
        train1.start();
        Thread.sleep(5);
        Locomotive l2 = locoFactory.createRandomLocomotive();
        MockTrain train2 = new MockTrain(1, link, l2);
        train2.start();
        Thread.sleep(5);
        assertEquals(1, link.getWaitingLocomotives().size());
        assertEquals(l2, link.getWaitingLocomotives().get(0));
        train2.setLeave(true);
        Thread.sleep(5);
        assertEquals(l2, link.getWaitingLocomotives().get(0));
    }
}