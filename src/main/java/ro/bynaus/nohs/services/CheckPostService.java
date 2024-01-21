package ro.bynaus.nohs.services;

import ro.bynaus.nohs.models.CheckPostInfo;

public interface CheckPostService {
    CheckPostInfo runPostCheck(ro.bynaus.nohs.entities.Service service, String origPost);
}
