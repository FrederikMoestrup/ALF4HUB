import {
  ModalOverlay,
  Modal,
  Button,
  Message,
} from "../styles/popUpMessageStyles";

const PopUpMessage = ({ isOpen, message, onClose, type }) => {
  if (!isOpen) return null;

  return (
    <ModalOverlay>
      <Modal>
        <Message type={type}>{message}</Message>
        <Button onClick={onClose}>Close</Button>
      </Modal>
    </ModalOverlay>
  );
};

export default PopUpMessage;
