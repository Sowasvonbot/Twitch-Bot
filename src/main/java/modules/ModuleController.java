package modules;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


/**
 * Import this interface if you make a new modul
 * It is higly recommend to use this interface, so all modules controllers are able to be easily loaded
 */
public interface ModuleController {


    /**
     * Returns the name of the module, maybe just Twitch for the Twitch API or Trollchat for a module that spams commands randomly
     * @return The name of the module as String
     */
    String getName();

    /**
     * This method will return a list of all PUBLIC methods with the required data. See the class MethodData for an overview of this object
     * @return A list of methodData with all public methods
     */
    List<MethodData> getMethods();

    /**
     * First you have to get all method names via getMethods and then you are able to execute one of them with this method
     * @param method The method to be executed as String
     * @return The return value specified in getMethods
     * @param inputs All required inputs for the given method, null will be accepted if no inputs are required
     */
    <T> T executeMethod(@Nonnull String method, List<?> inputs);

    /**
     * Basically the same like executeMethod, but the return is a CompletableFuture to be executed with a thread pool of your choice. The return type is also described in getMethods()
     * @param method The method to be executed as String
     * @param inputs All required inputs for the given method, null will be accepted if no inputs are required
     * @return The CompletableFuture with matching dataType of getMethods()
     */
    CompletableFuture<?>  executeMethodAsync(@Nonnull String method, List<?> inputs);

    /**
     * Another method to execute a given method. But this time you are able to give it a matching consumer. With this consumer you are able to handle the return instant
     * @param consumer The consumer, must match the return type of the function
     * @param method The method to be executed as String
     * @param inputs All required inputs for the given method, null will be accepted if no inputs are required
     * @return True, when consumer is matching and no other errors occurred
     */
    Boolean executeMethodWithConsumer(@Nonnull Consumer<?> consumer,@Nonnull String method, List<?> inputs);


    /**
     * This method will return the whole config description of the module. So all variables and all options have to be listed in this String
     * Also a how to use would be great.
     * @return the String with all config options
     */
    String getConfigDescription();

    /**
     * returns all ConfigVariables for the module and their values.
     * @return The Hashmap with the data. First the name, second the value
     */
    HashMap<String, String> getConfigVariables();

    /**
     * Sets the value for the given variable. Must be SUCCESS, when operation was successful
     * @param variable The name of the variable to be changed
     * @param value The value to be set
     * @return The response, maybe variable not found or value not accepted on failure, SUCCESS when successful
     */
    String setConfigVariable(String variable, String value);



}
