package com.packtpub.infinispan.chapter10.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({GenerateTicketsTest.class,
         	   QueryTicketsTest.class,
         	   DeleteTicketsTest.class, })
public class TicketCaseTestSuite {

}
