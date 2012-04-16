/* Copyright (c) 2001 - 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.crs;

import java.io.File;
import java.net.URL;
import org.geotools.data.DataUtilities;
import org.geotools.factory.Hints;
import org.geotools.referencing.factory.epsg.CoordinateOperationFactoryUsingWKT;
import org.opengis.referencing.operation.CoordinateOperationAuthorityFactory;
import org.vfny.geoserver.global.GeoserverDataDirectory;

/**
 * Authority allowing users to define their own CoordinateOperations in a separate file.
 * Will override EPSG definitions.
 * 
 * @author Oscar Fonts
 */
public class GeoserverWKTOperationFactory extends CoordinateOperationFactoryUsingWKT
        implements CoordinateOperationAuthorityFactory {

    /**
     * Priority for this factory.
     */
    public static final int PRIORITY = CoordinateOperationFactoryUsingWKT.PRIORITY + 10;
    
    public GeoserverWKTOperationFactory() {
        super(null, PRIORITY);
    }
    
    public GeoserverWKTOperationFactory(Hints userHints) {
        super(userHints, PRIORITY);
    }

    /**
     * Returns the URL to the property file that contains Operation definitions from
     * $GEOSERVER_DATA_DIR/user_projections/{@value #FILENAME}
     * @return The URL, or {@code null} if none.
     */
    protected URL getDefinitionsURL() {
        File file = new File(GeoserverDataDirectory.getGeoserverDataDirectory(),
                "user_projections/" + FILENAME);

        if (file.exists()) {
            return DataUtilities.fileToURL(file);
        } else {
            return null;
        }
    }
}
