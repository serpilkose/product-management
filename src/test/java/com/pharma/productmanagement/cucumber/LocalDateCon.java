package com.pharma.productmanagement.cucumber;

import cucumber.deps.com.thoughtworks.xstream.converters.Converter;
import cucumber.deps.com.thoughtworks.xstream.converters.MarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.converters.UnmarshallingContext;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamReader;
import cucumber.deps.com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateCon implements Converter {

    public boolean canConvert(Class type) {
        return LocalDate.class.isAssignableFrom(type);
    }

    private static final String            DEFAULT_DATE_PATTERN = "dd.MM.yyyy";
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN);

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        LocalDate  date = (LocalDate) value;
        String result = date.format(DEFAULT_DATE_FORMATTER);
        writer.setValue(result);
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        LocalDate result = LocalDate.parse(reader.getValue(), DEFAULT_DATE_FORMATTER);
        return result;
    }
}
