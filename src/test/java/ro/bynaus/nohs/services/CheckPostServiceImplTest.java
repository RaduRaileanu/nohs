package ro.bynaus.nohs.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.bynaus.nohs.entities.Service;
import ro.bynaus.nohs.integrations.OpenAiService;
import ro.bynaus.nohs.models.CheckPostInfo;
import ro.bynaus.nohs.models.OpenAiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CheckPostServiceImplTest {

    @Mock
    private OpenAiService openAiService;

    @InjectMocks
    private CheckPostServiceImpl checkPostService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRunPostCheckForService1() {
        // Mock data
        Service service = new Service();
        service.setId(1);
        String origPost = "Original post content";
        OpenAiResponse response = new OpenAiResponse();
        response.setMessage("0. no 1. 2. 3. 4. english");
        response.setCompletionTokens(30);
        response.setPromptTokens(30);

        // Mock OpenAiService call
        when(openAiService.callGpt(anyString())).thenReturn(response);

        // Perform the test
        CheckPostInfo result = checkPostService.runPostCheck(service, origPost);

        // Verify the results
        assertEquals(0.001 * response.getPromptTokens() + 0.002 * response.getCompletionTokens(), result.getCost());
    }

    @Test
    public void testRunPostCheckForService2() {
        // Mock data
        Service service = new Service();
        service.setId(2);
        String origPost = "Original post content";
        OpenAiResponse response = new OpenAiResponse();
        response.setMessage("0. no 1. 2.");
        response.setCompletionTokens(30);
        response.setPromptTokens(30);

        // Mock OpenAiService call
        when(openAiService.callGpt(anyString())).thenReturn(response);

        // Perform the test
        CheckPostInfo result = checkPostService.runPostCheck(service, origPost);

        // Verify the results
        assertEquals(0.001 * response.getPromptTokens() + 0.002 * response.getCompletionTokens(), result.getCost());
    }

    @Test
    public void testRunPostCheckForService3() {
        // Mock data
        Service service = new Service();
        service.setId(3);
        String origPost = "Original post content";
        OpenAiResponse response = new OpenAiResponse();
        response.setMessage("0. no ");
        response.setCompletionTokens(30);
        response.setPromptTokens(30);

        // Mock OpenAiService call
        when(openAiService.callGpt(anyString())).thenReturn(response);

        // Perform the test
        CheckPostInfo result = checkPostService.runPostCheck(service, origPost);

        // Verify the results
        assertEquals(0.001 * response.getPromptTokens() + 0.002 * response.getCompletionTokens(), result.getCost());
    }
}