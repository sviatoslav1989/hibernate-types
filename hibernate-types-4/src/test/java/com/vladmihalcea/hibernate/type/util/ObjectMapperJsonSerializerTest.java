package com.vladmihalcea.hibernate.type.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ObjectMapperJsonSerializerTest {

    private ObjectMapperJsonSerializer serializer = new ObjectMapperJsonSerializer(new ObjectMapperWrapper());

    @Test
    public void should_clone_serializable_object() {
        Object original = new SerializableObject("value");
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_non_serializable_object() {
        Object original = new NonSerializableObject("value");
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_collection_of_serializable_object() {
        List<SerializableObject> original = new ArrayList<SerializableObject>();
        original.add(new SerializableObject("value"));
        List<SerializableObject> cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_collection_of_non_serializable_object() {
        List<NonSerializableObject> original = new ArrayList<NonSerializableObject>();
        original.add(new NonSerializableObject("value"));
        List<NonSerializableObject> cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_empty_collection() {
        List<?> original = new ArrayList();
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_map_of_non_serializable_key() {
        Map<NonSerializableObject, String> original = new HashMap<NonSerializableObject, String>();
        original.put(new NonSerializableObject("key"), "value");
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_map_of_non_serializable_value() {
        Map<String, NonSerializableObject> original = new HashMap<String, NonSerializableObject>();
        original.put("key", new NonSerializableObject("value"));
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    public void should_clone_map_of_serializable_key_and_value() {
        Map<String, SerializableObject> original = new HashMap<String, SerializableObject>();
        original.put("key", new SerializableObject("value"));
        Object cloned = serializer.clone(original);
        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    private static class SerializableObject implements Serializable {
        private final String value;

        private SerializableObject(@JsonProperty("value") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SerializableObject that = (SerializableObject) o;

            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private static class NonSerializableObject {
        private final String value;

        private NonSerializableObject(@JsonProperty("value") String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NonSerializableObject that = (NonSerializableObject) o;

            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
