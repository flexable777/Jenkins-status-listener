package no.jitk;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class WebController {

    @Autowired
    private ArduinoSerialCommunicator communicator;

    @RequestMapping(value = "/jenkins", method = RequestMethod.POST)
    public void test(HttpServletRequest request)  throws Exception {

        Map map = request.getParameterMap();
        String[] data = (String[]) map.get("data");
        ObjectMapper objectMapper = new ObjectMapper();
        //Get JSon as object
        JenkinsStatus jenkinsStatus = objectMapper.readValue(data[0], JenkinsStatus.class);
        communicator.notifyArduino(jenkinsStatus);
    }

    @RequestMapping(value = "/changemode", method = RequestMethod.POST)
    public String changeMode(@RequestParam String mode) {
        if ("test".equals(mode)) {
            communicator.notifyArduino(ArduinoSerialCommunicator.Mode.TEST.getCode());
        } else if ("production".equals(mode)) {
            communicator.notifyArduino(ArduinoSerialCommunicator.Mode.PRODUCTION.getCode());
        }
        return "redirect:index";
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }
}
