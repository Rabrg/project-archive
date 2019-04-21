//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intellij.uiDesigner.core;

import java.awt.*;
import java.util.ArrayList;

public abstract class DimensionInfo {
    private final int[] myCell;
    private final int[] mySpan;
    protected final LayoutState myLayoutState;
    private final int[] myStretches;
    private final int[] mySpansAfterElimination;
    private final int[] myCellSizePolicies;
    private final int myGap;

    public DimensionInfo(LayoutState layoutState, int gap) {
        if(layoutState == null) {
            throw new IllegalArgumentException("layoutState cannot be null");
        } else if(gap < 0) {
            throw new IllegalArgumentException("invalid gap: " + gap);
        } else {
            this.myLayoutState = layoutState;
            this.myGap = gap;
            this.myCell = new int[layoutState.getComponentCount()];
            this.mySpan = new int[layoutState.getComponentCount()];

            int elimitated;
            for(elimitated = 0; elimitated < layoutState.getComponentCount(); ++elimitated) {
                GridConstraints i = layoutState.getConstraints(elimitated);
                this.myCell[elimitated] = this.getOriginalCell(i);
                this.mySpan[elimitated] = this.getOriginalSpan(i);
            }

            this.myStretches = new int[this.getCellCount()];

            for(elimitated = 0; elimitated < this.myStretches.length; ++elimitated) {
                this.myStretches[elimitated] = 1;
            }

            ArrayList var5 = new ArrayList();
            this.mySpansAfterElimination = (int[])((int[])this.mySpan.clone());
            Util.eliminate((int[])((int[])this.myCell.clone()), this.mySpansAfterElimination, var5);
            this.myCellSizePolicies = new int[this.getCellCount()];

            for(int var6 = 0; var6 < this.myCellSizePolicies.length; ++var6) {
                this.myCellSizePolicies[var6] = this.getCellSizePolicyImpl(var6, var5);
            }

        }
    }

    public final int getComponentCount() {
        return this.myLayoutState.getComponentCount();
    }

    public final Component getComponent(int componentIndex) {
        return this.myLayoutState.getComponent(componentIndex);
    }

    public final GridConstraints getConstraints(int componentIndex) {
        return this.myLayoutState.getConstraints(componentIndex);
    }

    public abstract int getCellCount();

    public abstract int getPreferredWidth(int var1);

    public abstract int getMinimumWidth(int var1);

    public abstract DimensionInfo getDimensionInfo(GridLayoutManager var1);

    public final int getCell(int componentIndex) {
        return this.myCell[componentIndex];
    }

    public final int getSpan(int componentIndex) {
        return this.mySpan[componentIndex];
    }

    public final int getStretch(int cellIndex) {
        return this.myStretches[cellIndex];
    }

    protected abstract int getOriginalCell(GridConstraints var1);

    protected abstract int getOriginalSpan(GridConstraints var1);

    abstract int getSizePolicy(int var1);

    abstract int getChildLayoutCellCount(GridLayoutManager var1);

    public final int getGap() {
        return this.myGap;
    }

    public boolean componentBelongsCell(int componentIndex, int cellIndex) {
        int componentStartCell = this.getCell(componentIndex);
        int span = this.getSpan(componentIndex);
        return componentStartCell <= cellIndex && cellIndex < componentStartCell + span;
    }

    public final int getCellSizePolicy(int cellIndex) {
        return this.myCellSizePolicies[cellIndex];
    }

    private int getCellSizePolicyImpl(int cellIndex, ArrayList eliminatedCells) {
        int policyFromChild = this.getCellSizePolicyFromInheriting(cellIndex);
        if(policyFromChild != -1) {
            return policyFromChild;
        } else {
            for(int i = eliminatedCells.size() - 1; i >= 0; --i) {
                if(cellIndex == ((Integer)eliminatedCells.get(i)).intValue()) {
                    return 1;
                }
            }

            return this.calcCellSizePolicy(cellIndex);
        }
    }

    private int calcCellSizePolicy(int cellIndex) {
        boolean canShrink = true;
        boolean canGrow = false;
        boolean wantGrow = false;
        boolean weakCanGrow = true;
        boolean weakWantGrow = true;
        int countOfBelongingComponents = 0;

        for(int i = 0; i < this.getComponentCount(); ++i) {
            if(this.componentBelongsCell(i, cellIndex)) {
                ++countOfBelongingComponents;
                int p = this.getSizePolicy(i);
                boolean thisCanShrink = (p & 1) != 0;
                boolean thisCanGrow = (p & 2) != 0;
                boolean thisWantGrow = (p & 4) != 0;
                if(this.getCell(i) == cellIndex && this.mySpansAfterElimination[i] == 1) {
                    canShrink &= thisCanShrink;
                    canGrow |= thisCanGrow;
                    wantGrow |= thisWantGrow;
                }

                if(!thisCanGrow) {
                    weakCanGrow = false;
                }

                if(!thisWantGrow) {
                    weakWantGrow = false;
                }
            }
        }

        return (canShrink?1:0) | (!canGrow && (countOfBelongingComponents <= 0 || !weakCanGrow)?0:2) | (!wantGrow && (countOfBelongingComponents <= 0 || !weakWantGrow)?0:4);
    }

    private int getCellSizePolicyFromInheriting(int cellIndex) {
        int nonInheritingComponentsInCell = 0;
        int policyFromInheriting = -1;

        for(int i = this.getComponentCount() - 1; i >= 0; --i) {
            if(this.componentBelongsCell(i, cellIndex)) {
                Component child = this.getComponent(i);
                GridConstraints c = this.getConstraints(i);
                Container container = findAlignedChild(child, c);
                if(container != null) {
                    GridLayoutManager grid = (GridLayoutManager)container.getLayout();
                    grid.validateInfos(container);
                    DimensionInfo info = this.getDimensionInfo(grid);
                    int policy = info.calcCellSizePolicy(cellIndex - this.getOriginalCell(c));
                    if(policyFromInheriting == -1) {
                        policyFromInheriting = policy;
                    } else {
                        policyFromInheriting |= policy;
                    }
                } else if(this.getOriginalCell(c) == cellIndex && this.getOriginalSpan(c) == 1 && !(child instanceof Spacer)) {
                    ++nonInheritingComponentsInCell;
                }
            }
        }

        if(nonInheritingComponentsInCell > 0) {
            return -1;
        } else {
            return policyFromInheriting;
        }
    }

    public static Container findAlignedChild(Component child, GridConstraints c) {
        if(c.isUseParentLayout() && child instanceof Container) {
            Container container = (Container)child;
            if(container.getLayout() instanceof GridLayoutManager) {
                return container;
            }

            if(container.getComponentCount() == 1 && container.getComponent(0) instanceof Container) {
                Container childContainer = (Container)container.getComponent(0);
                if(childContainer.getLayout() instanceof GridLayoutManager) {
                    return childContainer;
                }
            }
        }

        return null;
    }

    protected final Dimension getPreferredSize(int componentIndex) {
        Dimension size = this.myLayoutState.myPreferredSizes[componentIndex];
        if(size == null) {
            size = Util.getPreferredSize(this.myLayoutState.getComponent(componentIndex), this.myLayoutState.getConstraints(componentIndex), true);
            this.myLayoutState.myPreferredSizes[componentIndex] = size;
        }

        return size;
    }

    protected final Dimension getMinimumSize(int componentIndex) {
        Dimension size = this.myLayoutState.myMinimumSizes[componentIndex];
        if(size == null) {
            size = Util.getMinimumSize(this.myLayoutState.getComponent(componentIndex), this.myLayoutState.getConstraints(componentIndex), true);
            this.myLayoutState.myMinimumSizes[componentIndex] = size;
        }

        return size;
    }
}
