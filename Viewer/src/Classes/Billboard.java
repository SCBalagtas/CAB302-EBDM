package Classes;

import java.io.Serializable;

/**
 * Author: Steven Balagtas
 *
 * This class is the billboard object.
 */

public class Billboard implements Serializable {
    /**
     * Private fields for the billboard object.
     */
    private String billboardName;
    private String content;
    private String creator;

    /**
     * Constructor to create a new instance of a billboard.
     *
     * @param billboardName a string of the billboard's name.
     * @param content a string of the billboard's XML code.
     * @param creator a string of the username of the user who created the billboard.
     */
    public Billboard(String billboardName, String content, String creator) {
        this.billboardName = billboardName;
        this.content = content;
        this.creator = creator;
    }

    /**
     * Get the billboard's name.
     *
     * @return a string of the billboard's name.
     */
    public String getBillboardName() {
        return this.billboardName;
    }

    /**
     * Get the billboard's content.
     *
     * @return a string of the billboard's XML code.
     */
    public String getBillboardContent() {
        return this.content;
    }

    /**
     * Get the billboard's creator.
     *
     * @return a string of the creator's username.
     */
    public String getBillboardCreator() {
        return this.creator;
    }
}