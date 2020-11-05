import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IStand } from 'app/shared/model/stand.model';
import { getEntities as getStands } from 'app/entities/stand/stand.reducer';
import { getEntity, updateEntity, createEntity, reset } from './video-file.reducer';
import { IVideoFile } from 'app/shared/model/video-file.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVideoFileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVideoFileUpdateState {
  isNew: boolean;
  standId: string;
}

export class VideoFileUpdate extends React.Component<IVideoFileUpdateProps, IVideoFileUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      standId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getStands();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { videoFileEntity } = this.props;
      const entity = {
        ...videoFileEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/video-file');
  };

  render() {
    const { videoFileEntity, stands, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="standsVideouploadApp.videoFile.home.createOrEditLabel">Create or edit a VideoFile</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : videoFileEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="video-file-id">ID</Label>
                    <AvInput id="video-file-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="uuidLabel" for="video-file-uuid">
                    Uuid
                  </Label>
                  <AvField id="video-file-uuid" type="text" name="uuid" />
                </AvGroup>
                <AvGroup>
                  <Label id="sizeLabel" for="video-file-size">
                    Size
                  </Label>
                  <AvField id="video-file-size" type="string" className="form-control" name="size" />
                </AvGroup>
                <AvGroup>
                  <Label id="mimeTypeLabel" for="video-file-mimeType">
                    Mime Type
                  </Label>
                  <AvField id="video-file-mimeType" type="text" name="mimeType" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="video-file-startDate">
                    Start Date
                  </Label>
                  <AvField id="video-file-startDate" type="date" className="form-control" name="startDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="video-file-endDate">
                    End Date
                  </Label>
                  <AvField id="video-file-endDate" type="date" className="form-control" name="endDate" />
                </AvGroup>
                <AvGroup>
                  <Label for="video-file-stand">Stand</Label>
                  <AvInput id="video-file-stand" type="select" className="form-control" name="stand.id">
                    <option value="" key="0" />
                    {stands
                      ? stands.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/video-file" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  stands: storeState.stand.entities,
  videoFileEntity: storeState.videoFile.entity,
  loading: storeState.videoFile.loading,
  updating: storeState.videoFile.updating,
  updateSuccess: storeState.videoFile.updateSuccess
});

const mapDispatchToProps = {
  getStands,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoFileUpdate);
