package ir.rhotiz.test;

import ir.rhotiz.test.utils.Utils;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

public class ApacheCommonsConfigTest {
    @Test
    public void utilsWorkProperly() {
        String key = "utilKey";
        String value = "utilValue";
        String filePath ="src/test/resources/test-utils.properties";

        File f = new File(filePath);
        if(f.exists() && f.isFile()){
            f.delete();
        }

        Utils.writePropertyToFile(key,value,filePath);
        String extractedValue = Utils.readPropertyFromFile(key, filePath);
        assertThat(value,is(equalToIgnoringCase(extractedValue)));

    }


    @Test
    public void readPropertiesFromAFileInResources() throws ConfigurationException {
        Configurations configs = new Configurations();
        Configuration config = configs.properties(new File("file-in-resources.properties"));
        String host = config.getString("database.host");
        assertThat("db.acme.com", is(equalToIgnoringCase(host)));
    }

    @Test
    public void writeToAPropertyFileThatDoesNotExist() throws ConfigurationException, IOException {
        String filePath = "src/test/resources/";
        String fileName = "non-existing-file.properties";
        String fullPath = filePath+fileName;
        String key = "sampleKey";
        String value = "sampleValue";
        File file = new File(fullPath);
        if(file.exists() && file.isFile()){
            file.delete();
        }
        file.createNewFile();
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(fileName)
                                .setBasePath(filePath)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                        );
        Configuration config = builder.getConfiguration();


        config.setProperty(key, value);
        builder.save();
        String extractedValue = Utils.readPropertyFromFile(key, fullPath);
        assertThat(value,is(equalTo(extractedValue)));
    }


    @Test
    public void writeOnePropertyToPropertyFileThatExists() throws ConfigurationException {
        String key = "customKey";
        String value = "customValue";
        String basePath = "src/test/resources/";
        String fileName = "existing-file.properties";
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(fileName)
                                .setBasePath(basePath)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                        );
        Configuration config = builder.getConfiguration();


        config.setProperty(key, value);
        builder.save();
        String extractedValue = Utils.readPropertyFromFile(key, basePath+fileName);
        assertThat(value,is(equalTo(extractedValue)));
    }


    @Test
    public void readSeveralPropertiesFromAPropertiesFile() {
        fail("not implemented yet");
    }



    @Test
    public void writeSeveralPropertyToPropertyFile() throws ConfigurationException, IOException {
        String basePath = "src/test/resources/";
        String fileName = "write-several.properties";

        String fullPath = basePath+fileName;
        String key1 = "sampleKey1";
        String key2 = "sampleKey2";
        String key3 = "sampleKey3";
        String value1 = "sampleValue1";
        String value2 = "sampleValue2";
        String value3 = "sampleValue3";
        File file = new File(fullPath);
        if(file.exists() && file.isFile()){
            file.delete();
        }
        file.createNewFile();
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName(fileName)
                                .setBasePath(basePath)
                                .setListDelimiterHandler(new DefaultListDelimiterHandler(','))
                        );
        Configuration config = builder.getConfiguration();


        config.setProperty(key1, value1);
        config.setProperty(key2, value2);
        config.setProperty(key3, value3);
        builder.save();

        String extractedValue1 = Utils.readPropertyFromFile(key1, fullPath);
        assertThat(value1,is(equalTo(extractedValue1)));

        String extractedValue2 = Utils.readPropertyFromFile(key2, fullPath);
        assertThat(value2,is(equalTo(extractedValue2)));

        String extractedValue3 = Utils.readPropertyFromFile(key3, fullPath);
        assertThat(value3,is(equalTo(extractedValue3)));
    }

    @Test
    public void appendSeveralPropertyToAPropertyFile() {
        fail("not implemented yet");
    }

}
