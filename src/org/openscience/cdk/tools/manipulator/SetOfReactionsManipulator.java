/* $RCSfile$
 * $Author$ 
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 2003-2006  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  */
package org.openscience.cdk.tools.manipulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IElectronContainer;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.interfaces.IReactionSet;

/**
 * @cdk.module standard
 *
 * @see ChemModelManipulator
 */
public class SetOfReactionsManipulator {
    
    public static int getAtomCount(IReactionSet set) {
    	int count = 0;
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
        	count += ReactionManipulator.getAtomCount(reactions[i]);
        }
        return count;
    }

    public static int getBondCount(IReactionSet set) {
    	int count = 0;
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
        	count += ReactionManipulator.getBondCount(reactions[i]);
        }
        return count;
    }

    public static void removeAtomAndConnectedElectronContainers(IReactionSet set, IAtom atom) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            ReactionManipulator.removeAtomAndConnectedElectronContainers(reaction, atom);
        }
    }
    
    public static void removeElectronContainer(IReactionSet set, IElectronContainer electrons) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            ReactionManipulator.removeElectronContainer(reaction, electrons);
        }
    }
    
    /** 
     * @deprecated This method has a serious performace impact. Try to use
     *   other methods.
     */
    public static IAtomContainer getAllInOneContainer(IReactionSet set) {
        IAtomContainer container = set.getBuilder().newAtomContainer();
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            container.add(ReactionManipulator.getAllInOneContainer(reaction));
        }
        return container;
    }
    
    public static IMoleculeSet getAllMolecules(IReactionSet set) {
        IMoleculeSet moleculeSet = set.getBuilder().newMoleculeSet();
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            moleculeSet.add(ReactionManipulator.getAllMolecules(reaction));
        }
        return moleculeSet;
    }
    
    public static Vector getAllIDs(IReactionSet set) {
        Vector IDlist = new Vector();
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            IDlist.addAll(ReactionManipulator.getAllIDs(reaction));
        }
        return IDlist;
    }
    
    /**
     * Returns all the AtomContainer's of a Reaction.
     */
    public static IAtomContainer[] getAllAtomContainers(IReactionSet set) {
		return SetOfMoleculesManipulator.getAllAtomContainers(
            getAllMolecules(set)
        );
    }
    
    public static IReaction getRelevantReaction(IReactionSet set, IAtom atom) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            IAtomContainer container = ReactionManipulator.getRelevantAtomContainer(reaction, atom);
            if (container != null) { // a match!
                return reaction;
            }
        }
        return null;
    }

    public static IReaction getRelevantReaction(IReactionSet set, IBond bond) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            IAtomContainer container = ReactionManipulator.getRelevantAtomContainer(reaction, bond);
            if (container != null) { // a match!
                return reaction;
            }
        }
        return null;
    }

    public static IAtomContainer getRelevantAtomContainer(IReactionSet set, IAtom atom) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            IAtomContainer container = ReactionManipulator.getRelevantAtomContainer(reaction, atom);
            if (container != null) { // a match!
                return container;
            }
        }
        return null;
    }

    public static IAtomContainer getRelevantAtomContainer(IReactionSet set, IBond bond) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            IAtomContainer container = ReactionManipulator.getRelevantAtomContainer(reaction, bond);
            if (container != null) { // a match!
                return container;
            }
        }
        return null;
    }
    
    public static void setAtomProperties(IReactionSet set, Object propKey, Object propVal) {
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            ReactionManipulator.setAtomProperties(reaction, propKey, propVal);
        }
    }
    
    public static List getAllChemObjects(IReactionSet set) {
        ArrayList list = new ArrayList();
        IReaction[] reactions = set.getReactions();
        for (int i=0; i < reactions.length; i++) {
            IReaction reaction = reactions[i];
            list.addAll(ReactionManipulator.getAllChemObjects(reaction));
        }
        return list;
    }
    
}
