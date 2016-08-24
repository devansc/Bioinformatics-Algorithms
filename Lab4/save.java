
    private void printTree2(Node cur) {
        System.out.println("-------------------------");
        int startIdx = cur.getStartIdx();
        int endIdx = cur.getEndIdx();
        System.out.println("start:" + startIdx + (startIdx >= 0 ? sequence[startIdx] : "") +
                "  end: " + endIdx + (endIdx >= 0 ? sequence[endIdx] : ""));
        //System.out.println(cur.getEndIdx());
        //                               A     T      C      G      $
        boolean[] test = new boolean[]{false, false, false, false, false};

        if(cur instanceof InternalNode && ((InternalNode)cur).children[Nodes.A.ndx] != null) {
            System.out.println("a");
            test[0] = true;
        }
        if(cur instanceof InternalNode && ((InternalNode)cur).children[Nodes.T.ndx] != null) {
            System.out.println("t");
            test[1] = true;
        }
        if(cur instanceof InternalNode && ((InternalNode)cur).children[Nodes.C.ndx] != null) {
            System.out.println("c");
            test[2] = true;
        }
        if(cur instanceof InternalNode && ((InternalNode)cur).children[Nodes.G.ndx] != null) {
            System.out.println("g");
            test[3] = true;
        } 
        if(cur instanceof InternalNode && ((InternalNode)cur).children[Nodes.DOLLAR.ndx] != null) {
            System.out.println("$");
            test[4] = true;
        }

        if(test[0]) {
            System.out.println("children of a: ");
            printTree2(((InternalNode)cur).children[Nodes.A.ndx]);
        }
        if(test[1]) {
            System.out.println("children of t: ");
            printTree2(((InternalNode)cur).children[Nodes.T.ndx]);
        }
        if(test[2]) {
            System.out.println("children of c: ");
            printTree2(((InternalNode)cur).children[Nodes.C.ndx]);
        }
        if(test[3]) {
            System.out.println("children of g: ");
            printTree2(((InternalNode)cur).children[Nodes.G.ndx]);
        }
        if(test[4]) {
            System.out.println("children of $: ");
            printTree2(((InternalNode)cur).children[Nodes.DOLLAR.ndx]);
        }
    }
