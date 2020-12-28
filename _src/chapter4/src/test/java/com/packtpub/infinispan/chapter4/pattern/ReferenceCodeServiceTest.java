package com.packtpub.infinispan.chapter4.pattern;

import com.packtpub.infinispan.chapter4.exceptions.CodeDoesNotExistsException;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReferenceCodeServiceTest {
    static final ReferenceCodeService refCodeService = new ReferenceCodeService();

    @Test(expected = CodeDoesNotExistsException.class)
    public void testCacheEntryDoesNotExists() throws CodeDoesNotExistsException {
        String meaning = refCodeService.get("M");
        assertEquals(meaning, "Male");
    }

    @Test
    public void testCreateCacheAsideEntry() throws Exception {
        refCodeService.put("F", "Female");
    }

    @Test
    public void testGetCacheEntry() throws Exception {
       String meaning = refCodeService.get("F");
        assertEquals(meaning, "Female");
    }
}