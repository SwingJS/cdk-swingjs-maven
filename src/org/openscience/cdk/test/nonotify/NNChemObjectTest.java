/* $RCSfile$
 * $Author: egonw $    
 * $Date: 2006-04-12 11:16:35 +0200 (Wed, 12 Apr 2006) $    
 * $Revision: 5921 $
 * 
 * Copyright (C) 1997-2006  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 * 
 */
package org.openscience.cdk.test.nonotify;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IChemObjectChangeEvent;
import org.openscience.cdk.interfaces.IChemObjectListener;
import org.openscience.cdk.nonotify.NoNotificationChemObjectBuilder;
import org.openscience.cdk.test.ChemObjectTest;

/**
 * Checks the funcitonality of the AtomContainer.
 *
 * @cdk.module test-nonotify
 */
public class NNChemObjectTest extends ChemObjectTest {

    public NNChemObjectTest(String name) {
        super(name);
    }

    public void setUp() {
    	super.builder = NoNotificationChemObjectBuilder.getInstance();
    }

    public static Test suite() {
        return new TestSuite(NNChemObjectTest.class);
    }

    // Overwrite default methods: no notifications are expected!
    
    public void testNotifyChanged() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemObject chemObject = builder.newChemObject();
        chemObject.addListener(listener);
        
        chemObject.setID("Changed");
        assertFalse(listener.changed);
    }

    public void testNotifyChanged_IChemObjectChangeEvent() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemObject chemObject = builder.newChemObject();
        chemObject.addListener(listener);
        
        chemObject.setID("Changed");
        assertNull(listener.event);
    }

    public void testStateChanged_IChemObjectChangeEvent() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemObject chemObject = builder.newChemObject();
        chemObject.addListener(listener);
        
        chemObject.setID("Changed");
        assertFalse(listener.changed);
        
        listener.reset();
        assertFalse(listener.changed);
        chemObject.setProperty("Changed", "Again");
        assertFalse(listener.changed);

        listener.reset();
        assertFalse(listener.changed);
        chemObject.setFlag(3, true);
        assertFalse(listener.changed);
    }

    public void testClone_ChemObjectListeners() throws Exception {
        IChemObject chemObject1 = builder.newChemObject();
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        chemObject1.addListener(listener);
        IChemObject chemObject2 = (IChemObject)chemObject1.clone();

        // test lack of cloning of listeners
        assertEquals(0, chemObject1.getListenerCount());
        assertEquals(0, chemObject2.getListenerCount());
    }
    
    public void testAddListener_IChemObjectListener() {
        IChemObject chemObject1 = builder.newChemObject();
        assertEquals(0, chemObject1.getListenerCount());
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        chemObject1.addListener(listener);
        assertEquals(0, chemObject1.getListenerCount());
    }
    
    public void testGetListenerCount() {
        IChemObject chemObject1 = builder.newChemObject();
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        chemObject1.addListener(listener);
        assertEquals(0, chemObject1.getListenerCount());
    }

    public void testRemoveListener_IChemObjectListener() {
        IChemObject chemObject1 = builder.newChemObject();
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        chemObject1.addListener(listener);
        assertEquals(0, chemObject1.getListenerCount());
        chemObject1.removeListener(listener);
        assertEquals(0, chemObject1.getListenerCount());
    }
    
    public void testSetNotification_true() {
        ChemObjectListenerImpl listener = new ChemObjectListenerImpl();
        IChemObject chemObject = builder.newChemObject();
        chemObject.addListener(listener);
        chemObject.setNotification(true);
        
        chemObject.setID("Changed");
        assertFalse(listener.changed);
    }
    
    private class ChemObjectListenerImpl implements IChemObjectListener {
        private boolean changed;
        private IChemObjectChangeEvent event;
        
        private ChemObjectListenerImpl() {
            changed = false;
            event = null;
        }
        
        public void stateChanged(IChemObjectChangeEvent e) {
            changed = true;
            event = e;
        }
        
        public void reset() {
            changed = false;
            event = null;
        }
    }
}
