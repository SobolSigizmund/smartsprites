package org.carrot2.labs.test;

import java.util.*;

import org.apache.commons.lang.builder.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * A helper class for testing collections of classes that do not need to define
 * {@link Object#equals(Object)} and {@link Object#hashCode()} at runtime, but would be
 * much easier to test if such methods were defined. This class implements a
 * reflection-based versions of these methods.
 */
// TODO: Refactor this to use custom Message assertions plugin
public class TestEqualsHelper
{
    @SuppressWarnings("unused")
    private final Object delegate;

    TestEqualsHelper(Object delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public boolean equals(Object obj)
    {
        return EqualsBuilder.reflectionEquals(this.delegate,
            ((TestEqualsHelper) obj).delegate);
    }

    @Override
    public int hashCode()
    {
        return HashCodeBuilder.reflectionHashCode(delegate);
    }

    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(delegate);
    }

    public static Collection<Object> wrap(Collection<?> collection)
    {
        Collection<Object> wrapped;
        if (collection instanceof ArrayList)
        {
            wrapped = Lists.newArrayList();
        }
        else if (collection instanceof LinkedList)
        {
            wrapped = Lists.newLinkedList();
        }
        else if (collection instanceof HashSet)
        {
            wrapped = Sets.newHashSet();
        }
        else
        {
            throw new UnsupportedOperationException("List type: " + collection.getClass()
                + " not supported.");
        }

        for (final Object object : collection)
        {
            wrapped.add(wrap(object));
        }

        return wrapped;
    }

    public static TestEqualsHelper wrap(Object object)
    {
        return new TestEqualsHelper(object);
    }
}
