package cs3500.animator.model;


/**
 * This interface contains the animation model methods that is implemented by our
 * view model class in order to ensure that our view and model are not directly linked
 * and that the model can't be directly mutated by the view. We made this so that our
 * customer is able to easily mimic a view model of our own.
 */
public interface IViewModel extends AnimationModel {


}
