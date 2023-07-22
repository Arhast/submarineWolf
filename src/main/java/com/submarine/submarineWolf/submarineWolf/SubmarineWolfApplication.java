package com.submarine.submarineWolf.submarineWolf;

import com.submarine.submarineWolf.submarineWolf.config.Config;
import com.submarine.submarineWolf.submarineWolf.core.WolfInfo;
import com.submarine.submarineWolf.submarineWolf.service.Messaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class SubmarineWolfApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubmarineWolfApplication.class, args);
	}

	@Bean()
	@Qualifier("WolfInfo")
	public WolfInfo getSomeService(ApplicationArguments arguments) throws IOException {
		String wolfName = arguments.getSourceArgs()[0];
		String wolfPath = arguments.getSourceArgs()[1];
		WolfInfo wolfInfo = new WolfInfo();
		wolfInfo.setWolfName(wolfName);
		wolfInfo.setPath(wolfPath);
		return wolfInfo;
	}

}
