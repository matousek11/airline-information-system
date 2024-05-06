package org.skywise.skywise.services;

public class ValidatorService {
    public boolean validateFlightParameters(
            long pilotID,
            long copilotID
    ) {
        return pilotID != copilotID;
    }
}
