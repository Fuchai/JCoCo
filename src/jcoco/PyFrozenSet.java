package jcoco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PyFrozenSet extends PyPrimitiveTypeAdapter {

    private HashSet<PyObject> data;

    // TODO
    public PyFrozenSet(HashSet<PyObject> data) {
        super("frozen_set", PyType.PyTypeId.PyFrozenSetType);
        this.data = data;
        initMethods(funs());
    }

    // Done
    public PyFrozenSet(){
        this(new HashSet<PyObject>());
    }

//    public PyObject getVal(int index) {
//        if (index >= this.data.size()) {
//            throw new PyException(PyException.ExceptionType.PYSTOPITERATIONEXCEPTION,
//                    "Stop iteration: index out of range");
//        }
//        return this.data.get(index);
//    }
//
//    public void setVal(int index, PyObject val) {
//        this.data.set(index, val);
//    }

    public int len() {
        return this.data.size();
    }

    public HashSet<PyObject> hashSet() {
        return this.data;
    }

    @Override
    public String str() {
        String str = "frozenset({";
        ArrayList<PyObject> args = new ArrayList<PyObject>();
        int i =0;
        for (PyObject obj:data) {
            // Creating a new call stack here is a trade-off. This simplifies
            // the interface to the str() method, for instance. It only affects
            // usage of the debugger. Exceptions will still have the full traceback
            // but if the debugger is used, the call stack will stop at this call
            // for calls to "__repr__" in this case.
            str += ((PyStr) obj.callMethod(new PyCallStack(), "__repr__", args)).str();

            if (i < data.size() - 1) {
                str += ", ";
            }

            i+=1;
        }

        str += "})";

        return str;
    }

    public static HashMap<String, PyCallable> funs() {
        HashMap<String, PyCallable> funs = new HashMap<String, PyCallable>();

//        funs.put("__getitem__", new PyCallableAdapter() {
//            @Override
//            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
//                if (args.size() != 2) {
//                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
//                            "TypeError: expected 2 argument, got " + args.size());
//                }
//
//                PyList self = (PyList) args.get(args.size() - 1);
//                PyInt intObj = (PyInt) args.get(0);
//
//                return self.getVal(intObj.getVal());
//            }
//        });
//        funs.put("__setitem__", new PyCallableAdapter() {
//            @Override
//            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
//                if (args.size() != 3) {
//                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
//                            "TypeError: expected 3 arguments, got " + args.size());
//                }
//
//                PyList self = (PyList) args.get(args.size() - 1);
//                PyInt index = (PyInt) args.get(1);
//
//                if (index.getVal() >= self.data.size()) {
//                    throw new PyException(PyException.ExceptionType.PYILLEGALOPERATIONEXCEPTION, "List index out of bounds, size=" + self.data.size() + ", index=" + index.str());
//                }
//                //set the object at the index of the first argument to the second arg
//                self.setVal(index.getVal(), args.get(0));
//
//                return new PyNone();
//            }
//        });
        // Done
        funs.put("__len__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 1) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 1 arguments, got " + args.size());
                }

                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);
                return new PyInt(self.data.size());
            }
        });
        // Done
        funs.put("union", new PyCallableAdapter() {
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 2) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 2 arguments, got " + args.size());
                }

                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);

                HashSet<PyObject> result = (HashSet<PyObject>) self.data.clone();
                for (int i=0;i<args.size()-1;i++){
                    result.addAll(((PyFrozenSet) args.get(i)).data);
                }
                return new PyFrozenSet(result);
            }
        });
        // Done
        funs.put("intersection", new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 2) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 2 arguments, got " + args.size());
                }

                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);

                HashSet<PyObject> result = (HashSet<PyObject>) self.data.clone();
                for (int i = 0; i < args.size() - 1; i++) {
                    result.retainAll(((PyFrozenSet) args.get(i)).data);
                }
                return new PyFrozenSet(result);
            }
        });
        // Done
        funs.put("isdisjoint", new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size()!=2){
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 2 arguments, got " + args.size());
                }
                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);

                HashSet<PyObject> result = (HashSet<PyObject>) self.data.clone();
                for (int i = 0; i < args.size() - 1; i++) {
                    result.retainAll(((PyFrozenSet) args.get(i)).data);
                }
                return new PyBool(result.isEmpty());
            }
        });
        funs.put("difference", new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                return super.__call__(callStack, args);
            }
        });

        funs.put("symmetric_difference", new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                return super.__call__(callStack, args);
            }
        });
        funs.put("issuperset", new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                return super.__call__(callStack, args);
            }
        });
        funs.put('__contains__', new PyCallableAdapter(){
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                return super.__call__(callStack, args);
            }
        })
        // Done
        funs.put("__iter__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 1) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 1 arguments, got " + args.size());
                }

                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);
                return new PyFrozenSetIterator(self);
            }
        });
        // Done
//
//        funs.put("append", new PyCallableAdapter() {
//            @Override
//            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
//                if (args.size() != 2) {
//                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
//                            "TypeError: expected 2 arguments, got " + args.size());
//                }
//
//                PyList self = (PyList) args.get(args.size() - 1);
//                self.data.add(args.get(0));
//
//                return new PyNone();
//            }
//        });
//
//        funs.put("__add__", new PyCallableAdapter() {
//            @Override
//            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
//                if (args.size() != 2) {
//                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
//                            "TypeError: expected 2 arguments, got " + args.size());
//                }
//
//                PyList self = (PyList) args.get(args.size() - 1);
//                PyObject arg = args.get(0);
//
//                if (args.get(0).getType().typeId() != PyType.PyTypeId.PyListType) {
//                    throw new PyException(PyException.ExceptionType.PYILLEGALOPERATIONEXCEPTION,
//                            "TypeError: unsupported operand type(s) for +: 'list' and '" + arg.getType().str() + "'");
//                }
//                PyList other = (PyList) arg;
//
//                ArrayList<PyObject> result = (ArrayList<PyObject>) self.data.clone();
//                result.addAll(other.data);
//                return new PyList(result);
//            }
//        });
//
//        funs.put("__mul__", new PyCallableAdapter() {
//            @Override
//            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
//                if (args.size() != 2) {
//                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
//                            "TypeError: expected 2 arguments, got " + args.size());
//                }
//
//                PyList self = (PyList) args.get(args.size() - 1);
//                PyObject arg = args.get(0);
//
//                if (args.get(0).getType().typeId() != PyType.PyTypeId.PyIntType) {
//                    throw new PyException(PyException.ExceptionType.PYILLEGALOPERATIONEXCEPTION,
//                            "TypeError: unsupported operand type(s) for +: 'list' and '" + arg.getType().str() + "'");
//                }
//                PyInt other = (PyInt) arg;
//
//                ArrayList<PyObject> result = new ArrayList<PyObject>();
//
//                for (int k = 0; k < other.getVal(); k++) {
//                    result.addAll(self.data);
//                }
//
//                return new PyList(result);
//            }
//        });


        // Done
        funs.put("__eq__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 2) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 2 arguments, got " + args.size());
                }

                PyFrozenSet self = (PyFrozenSet) args.get(args.size() - 1);

                //We should check the type of args[0] before casting it.
                if (self.getType().typeId() != args.get(0).getType().typeId()) {
                    return new PyBool(false);
                }

                PyFrozenSet other = (PyFrozenSet) args.get(0);
                ArrayList<PyObject> newargs = new ArrayList<PyObject>();

                if (self.data.size() != other.data.size()) {
                    return new PyBool(false);
                }

//                for (int i = 0; i < self.data.size(); i++) {
//                    newargs.add(other.data.get(i));
//                    PyBool result = (PyBool) self.data.get(i).callMethod(callStack, "__eq__", newargs);
//                    if (!result.getVal()) {
//                        return result;
//                    }
//
//                    newargs.remove(newargs.size() - 1); // remove the argument from the vector
//                }

                return new PyBool(self.data.equals(other.data));
                // not sure how the comparison is implemented in Python.
                // real signature unknown, could be implemented by C

            }
        });

        // Done
        funs.put("__ne__", new PyCallableAdapter() {
            @Override
            public PyObject __call__(PyCallStack callStack, ArrayList<PyObject> args) {
                if (args.size() != 2) {
                    throw new PyException(PyException.ExceptionType.PYWRONGARGCOUNTEXCEPTION,
                            "TypeError: expected 2 arguments, got " + args.size());
                }

                PyList self = (PyList) args.get(args.size() - 1);
                PyBool result = (PyBool) self.callMethod(callStack, "__eq__", selflessArgs(args));
                boolean v = result.getVal();

                if (v) {
                    return new PyBool(false);
                }

                return new PyBool(true);
            }
        });

        return funs;
    }
}
