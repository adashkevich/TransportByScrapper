package com.adashkevich.transport_by_parser;

import com.adashkevich.transport_by_parser.model.gtfs.GRoute;
import com.adashkevich.transport_by_parser.model.gtfs.emum.GRouteType;
import com.adashkevich.transport_by_parser.utils.CsvUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;

public class CsvUtilTest {

    @Test
    public void readTest() throws Exception {
        List<GRoute> routs = CsvUtil.read(Paths.get("src/test/resources/gtfs/routes.txt"), GRoute.class);

        Assert.assertEquals(1, routs.size());

        Assert.assertEquals("gomel_get", routs.get(0).getAgencyID());
        Assert.assertEquals("008AFF", routs.get(0).getRouteColor());
        assert (StringUtils.isBlank(routs.get(0).getRouteDesc()));
        Assert.assertEquals("1234567", routs.get(0).getRouteID());
        Assert.assertEquals("Вокзал — Предприятие «Салео-Гомель»", routs.get(0).getRouteLongName());
        Assert.assertEquals("1", routs.get(0).getRouteShortName());
        Assert.assertEquals("A9A9A9", routs.get(0).getRouteTextColor());
        Assert.assertEquals(11, routs.get(0).getRouteType());
        Assert.assertEquals(GRouteType.TROLLEYBUS, routs.get(0).getRouteTypeEnum());
        Assert.assertEquals("https://gomeltrans.net/routes/trolleybus/1/", routs.get(0).getRouteUrl());
    }

    public void writeTest() {
        // TODO implement
    }
}
