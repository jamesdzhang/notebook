package test.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.net.URL;

import com.nb.james.springboot.service.IService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(classes=com.nb.james.springboot.main.SpringBootMain.class)
public class HelloControllerIT {

    private int port;

    private URL base;

//    @Resource
//    private TestRestTemplate template;

    @Autowired
    IService service;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + 8888 + "/springBoot/hello");
    }

    @Test
    public void getHello() throws Exception {
        /*ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));*/
        String result = service.hello();
        assertThat(result, equalTo("hi"));
    }
}