import {TouchableOpacity} from 'react-native';
import React, {ReactElement, ReactNode} from 'react';

type CustomButtonProps = {
  children: ReactElement | ReactNode;
  style?: string;
};

const CustomButton = (props: CustomButtonProps) => {
  return (
    <TouchableOpacity className={props.style!}>
      {props.children}
    </TouchableOpacity>
  );
};

export default CustomButton;
