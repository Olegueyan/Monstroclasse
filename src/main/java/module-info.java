module net.olegueyan.monstroclasse {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.speedment.common.tuple;
    requires com.google.gson;
    requires org.jetbrains.annotations;
    requires org.slf4j;
    requires java.logging;
    requires java.sql;
    requires java.desktop;
    requires ormlite.jdbc;
    requires org.xerial.sqlitejdbc;
    requires zip4j;
    requires org.apache.commons.csv;
    requires org.apache.poi.poi;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j.plugins;

    opens net.olegueyan.monstroclasse to javafx.fxml;
    exports net.olegueyan.monstroclasse;

    exports net.olegueyan.monstroclasse.common;
    exports net.olegueyan.monstroclasse.component;
    exports net.olegueyan.monstroclasse.entity;
    exports net.olegueyan.monstroclasse.entry;
    exports net.olegueyan.monstroclasse.event;
    exports net.olegueyan.monstroclasse.event.ecrilu;
    exports net.olegueyan.monstroclasse.function;
    exports net.olegueyan.monstroclasse.integration;
    exports net.olegueyan.monstroclasse.json.ecrilu;
    exports net.olegueyan.monstroclasse.json.ecritoire;
    exports net.olegueyan.monstroclasse.module;
    exports net.olegueyan.monstroclasse.module.content;
    exports net.olegueyan.monstroclasse.motor.core;
    exports net.olegueyan.monstroclasse.motor;
    exports net.olegueyan.monstroclasse.node;
    exports net.olegueyan.monstroclasse.node.content.ecrilu.ecritoire;
    exports net.olegueyan.monstroclasse.node.partial;
    exports net.olegueyan.monstroclasse.portal;
    exports net.olegueyan.monstroclasse.repository;
    exports net.olegueyan.monstroclasse.screen;
    exports net.olegueyan.monstroclasse.service;
    exports net.olegueyan.monstroclasse.utils;

    opens net.olegueyan.monstroclasse.entity to ormlite.jdbc;
    exports net.olegueyan.monstroclasse.node.content;
    exports net.olegueyan.monstroclasse.node.content.ecrilu;
    exports net.olegueyan.monstroclasse.node.content.ecrilu.pupil;
}