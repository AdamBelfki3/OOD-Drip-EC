Submit a README file summarizing which
features you were able to get working successfully
and which features do not work in your final submission.

We were able to get all the features working successfully but in order
to do so, we had to add a few methods to their interface. Since they decided
to have their Motion getter fields return a change/delta of each of the shape
features and accumulate accordingly, we had to request our provider to allow us to
add “start” and “end” methods for each since our model does not accumulate like theirs
do. Instead, our model takes in the “start” and “end” values and ensures that there are
no gaps being created so we had to request them to allow us to add these methods so
that we don’t have to alter our model. Additionally, the other problem we had was
that their controller has the timer but ours is stored in the view so we had to
make an interactive view provider adaptor that implements our visual animation view
but passes in their view so that we can use that in the controller to mimic their
controller behavior. In other words, we made the controller implement the features
methods directly since the timer is stored in the controller and then the interactive
view adaptor simply added the features methods to the view by passing in the controller
(features object) in the add features method and so the interactive view was successfully able to work.
We didn’t face any other problems and were able to make our provider’s interactive and
textual views working successfully with our code except that the interactive hybrid
version does not work with keys since they most probably forgot to add that part but all
the other features (buttons) work perfectly!
