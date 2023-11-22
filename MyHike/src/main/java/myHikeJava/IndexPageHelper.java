package myHikeJava;

import jakarta.servlet.http.HttpServletRequest;

public class IndexPageHelper {
    public static String tryGetSuccessMessage(HttpServletRequest request) {
        //Try to get parameters "createSuccess" and "editSuccess" based on these parameters a success alert
        //will be displayed as a follow-up for either the create.jsp or the edit.jsp page.
        boolean createSuccess = Boolean.parseBoolean(request.getParameter("createSuccess"));
        boolean editSuccess = Boolean.parseBoolean(request.getParameter("editSuccess"));
        boolean deleteSuccess = Boolean.parseBoolean(request.getParameter("deleteSuccess"));
        String alertMessage = "";
        if (createSuccess)  {
            return "Successfully created your new hike - you should now be able to see it in the discovery tab.";
        } else if (editSuccess) {
            return "Successfully edited your hike - you can find it and view your changes in the discovery tab.";
        } else if (deleteSuccess) {
            return "Successfully deleted your hike.";
        }
        return alertMessage;
    }
}
