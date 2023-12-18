package com.enigma.shopeymart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.plaf.PanelUI;

@RestController // anotasi controller dari spring
public class HelloController {
    @GetMapping(value = "/hello") // Get mapping digunakan untuk memberitahu spring method apa yang digunkan
    public String hello() {  // value diisi dengan path penamaan path menggunkan noun (kata benda)
        return "<h1> Hello World !!! </h1>";
    }

    @GetMapping(value = "/hello/v1")// value ini jangan dikosongin!! klo ga kepake komen aja!
    public String[] getHobbies() {
        return new String[]{"Makan", "Tidur"};
    }

    @GetMapping("/search{key}")
    public String getRequestParam(@RequestParam String key) {
        return key;
    }

    @GetMapping("/data/{id}")
    public String getDataById(@PathVariable String id) {
        return "data " + id;
    }
}
