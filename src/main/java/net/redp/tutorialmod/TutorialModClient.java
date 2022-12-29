package net.redp.tutorialmod;

import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TutorialModClient implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(TutorialModClient.class);
    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    @Override
    public void onInitializeClient() {

    }

    // other fields and methods go here
}
