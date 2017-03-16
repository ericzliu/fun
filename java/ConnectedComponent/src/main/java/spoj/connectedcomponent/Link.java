/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.connectedcomponent;

public class Link {
    final Node u;
    final Node v;

    public Link(Node u, Node v) {
        this.u = u;
        this.v = v;
    }

    public Node getU() {
        return u;
    }

    public Node getV() {
        return v;
    }
    
}
