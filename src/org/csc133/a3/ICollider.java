package org.csc133.a3;

public interface ICollider {

     boolean collidesWith(GameObject thatObject);

     void handleCollision(GameObject thatObject);

}
