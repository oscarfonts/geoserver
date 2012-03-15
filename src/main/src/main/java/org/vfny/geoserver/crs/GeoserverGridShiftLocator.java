/* Copyright (c) 2001 - 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, availible at the root
 * application directory.
 */
package org.vfny.geoserver.crs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import javax.imageio.spi.ServiceRegistry;

import org.geotools.data.DataUtilities;
import org.geotools.factory.AbstractFactory;
import org.geotools.factory.Factory;
import org.geotools.metadata.iso.citation.Citations;
import org.geotools.referencing.factory.gridshift.ClasspathGridShiftLocator;
import org.geotools.referencing.factory.gridshift.GridShiftLocator;
import org.opengis.metadata.citation.Citation;
import org.vfny.geoserver.global.GeoserverDataDirectory;

/**
 * Provides a hook to locate grid shift files, such as NTv1, NTv2 and NADCON ones.
 * 
 * @author Andrea Aime - Geosolutions
 * @author Oscar Fonts - geomati.co
 */
public class GeoserverGridShiftLocator extends AbstractFactory implements GridShiftLocator {

    /** 
     * Higher PRIORITY than default locator
     */
    public static final int PRIORITY = ClasspathGridShiftLocator.PRIORITY + 10;
    
    public GeoserverGridShiftLocator() {
        super(PRIORITY);
    }

    @Override
    public Citation getVendor() {
        return Citations.GEOTOOLS; // Should be GEOSERVER? How?
    }
    
    /**
     * Locate the specified grid file.
     * 
     * It will look in GEOSERVER_DATA_DIR/user_projections
     * 
     * @param grid the grid name/location
     * @return the fully resolved URL of the grid or null, if the resource cannot be located.
     */
    @Override
    public URL locateGrid(String grid) {
        if (grid == null)
           return null;
        
        File gridfile = new File(GeoserverDataDirectory.getGeoserverDataDirectory(),
                "user_projections/"+grid);

        if (gridfile.exists()) {
            return DataUtilities.fileToURL(gridfile);
        } else {
            return null;            
        }
    }
}
