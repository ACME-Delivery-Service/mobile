package com.swa.swamobileteam.data.deliveries;

import com.swa.swamobileteam.ui.deliveryGroups.DeliveriesListItem;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.junit.Assert.*;

public class DeliveryScheduleRepositoryImplTest {

// TODO: inject API tokens for these tests to pass
//    @Test
//    public void refresh() {
//        TestObserver<Void> observer = new TestObserver<Void>();
//        DeliveryScheduleRepository repository = new DeliveryScheduleRepository();
//        repository.refresh().subscribe(observer);
//        observer.assertComplete();
//    }

//    @Test
//    public void getDeliveryListItem() {
//        ArrayList<DeliveriesListItem> generatedData = generateData();
//        DeliveryScheduleRepository repository = new DeliveryScheduleRepository();
//        assertNotNull(repository.getDeliveryListItem(0));
//    }
//
//    @Test
//    public void loadDeliveries() {
//        DeliveryScheduleRepository repository = new DeliveryScheduleRepository();
//        TestObserver<Integer> observer = repository.loadDeliveries().test().assertSubscribed();
//    }

//    @Test
//    public void getDeliveriesSchedule2() {
//        DeliveryScheduleRepository deliveryScheduleRepository = new DeliveryScheduleRepository();
//        TestObserver<List<DeliveriesListItem>> observer = deliveryScheduleRepository.getDeliveriesSchedule(200, 200).test();
//    }


    private ArrayList<DeliveriesListItem> generateData() {

        String stringStartDate1 = "10:09:2018, 23:37";
        String stringEndDate1 = "11:09:2018, 09:10";
        Date startDate1 = new Date();
        Date finishDate1 = new Date();

        String stringStartDate2 = "11:09:2018, 10:15";
        String stringEndDate2 = "11:09:2018, 12:10";
        Date startDate2 = new Date();
        Date finishDate2 = new Date();

        String stringStartDate3 = "11:09:2018, 13:20";
        String stringEndDate3 = "11:09:2018, 14:00";
        Date startDate3 = new Date();
        Date finishDate3 = new Date();

        String stringStartDate4 = "11:09:2018, 15:00";
        String stringEndDate4 = "11:09:2018, 18:10";
        Date startDate4 = new Date();
        Date finishDate4 = new Date();

        String stringStartDate5 = "11:09:2018, 21:00";
        String stringEndDate5 = "12:09:2018, 10:00";
        Date startDate5 = new Date();
        Date finishDate5 = new Date();

        String stringStartDate6 = "12:09:2018, 10:00";
        String stringEndDate6 = "12:09:2018, 11:30";
        Date startDate6 = new Date();
        Date finishDate6 = new Date();

        try {
            startDate1 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate1);
            finishDate1 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate1);

            startDate2 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate2);
            finishDate2 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate2);

            startDate3 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate3);
            finishDate3 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate3);

            startDate4 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate4);
            finishDate4 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate4);

            startDate5 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate5);
            finishDate5 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate5);

            startDate6 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringStartDate6);
            finishDate6 = new SimpleDateFormat("dd:MM:yyyy, HH:mm").parse(stringEndDate6);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Location location1 = new Location(51.520664584, -0.15499938);
        Location location2 = new Location(100.520664584, 0.35499938);
        Location location3 = new Location(234.520664584, 123.15499938);
        Location location4 = new Location(20.520664584, 230.15499938);
        Location location5 = new Location(220.520664584, 23.15499938);
        Location location6 = new Location(120.520664584, 123.15499938);

        Address address6 = new Address("New York, Baker Street, 221b", location1);
        Address address5 = new Address("Innopolis, Universitetskaya, 1", location2);
        Address address4 = new Address("Kazan, Kremlevskaya, 1", location3);
        Address address3 = new Address("Tataratan, Vvedenskaya Sloboda, 3", location4);
        Address address2 = new Address("Kemerovo, Centralnaya, 10", location5);
        Address address1 = new Address("Kemerovo, Centralnaya, 10", location6);

        DeliveryPeriod deliveryPeriod1 = new DeliveryPeriod(startDate1, finishDate1);
        DeliveryPeriod deliveryPeriod2 = new DeliveryPeriod(startDate2, finishDate2);
        DeliveryPeriod deliveryPeriod3 = new DeliveryPeriod(startDate3, finishDate3);
        DeliveryPeriod deliveryPeriod4 = new DeliveryPeriod(startDate4, finishDate4);
        DeliveryPeriod deliveryPeriod5 = new DeliveryPeriod(startDate5, finishDate5);
        DeliveryPeriod deliveryPeriod6 = new DeliveryPeriod(startDate6, finishDate6);

        DeliveriesListItem item1 = new DeliveriesListItem("1024843523", address1,
                deliveryPeriod1, true, 6.1);
        DeliveriesListItem item2 = new DeliveriesListItem("1024843524", address2,
                deliveryPeriod2, true, 40.3);
        DeliveriesListItem item3 = new DeliveriesListItem("1024843525", address3,
                deliveryPeriod3, true, 12.7);
        DeliveriesListItem item4 = new DeliveriesListItem("1024843526", address4,
                deliveryPeriod4, true, 2.0);
        DeliveriesListItem item5 = new DeliveriesListItem("1024843527", address5,
                deliveryPeriod5, true, 8.5);
        DeliveriesListItem item6 = new DeliveriesListItem("1024843528", address6,
                deliveryPeriod6, true, 10.5);

        return new ArrayList<DeliveriesListItem>(Arrays.asList(item1, item2, item3, item4, item5, item6));
    }
}