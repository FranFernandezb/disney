package com.alkemy.disney.utils.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


public class Object {
    protected Logger log = LogManager.getLogger(this.getClass());

   protected ObjectMapper mapper = new ObjectMapper();

}
